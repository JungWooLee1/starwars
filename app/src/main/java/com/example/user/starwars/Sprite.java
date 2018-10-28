package com.example.user.starwars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.example.user.starwars.MapTask.SinglePath;

import java.util.Random;

/**
 * Created by user on 2018-06-09.
 */

public class Sprite {

    final static int ENTER = 1;                           // 캐릭터 입장
    final static int BEGINPOS = 2;                     // 전투 대형으로 가기 위해 좌표 계산
    final static int POSITION = 3;                      // 전투 대형으로 이동 중
    final static int SYNC = 4;                            // 전투 대형에서 대기중
    final static int ATTACK = 5;                          // 공격중
    final static int BEGINBACK = 6;                    // View를 벗어나서 다시 입장할 준비(탈영)
    final static int BACKPOS = 7;                      // 다시 입장 중(탈영병 복귀)

    public int status;                                        // 캐릭터의 상태(위의 1~7)
    public int x, y;                                          // 좌표
    public int w, h;                                          // 크기
    public boolean isDead;                                   // 사망
    public int shield;                                        // 보호막
    private SinglePath sPath;                                 // 캐릭터가 이동할 Path 1줄 (입장 및 공격 루트)
    private float sx, sy;                                    // 캐릭터 이동 속도
    private int sncX;                                        // 싱크 위치로부터 떨어져 있는 거리
    public Bitmap imgSprite;                                 // 현재 방향의 이미지
    private Bitmap imgSpt[] = new Bitmap[16];    // 16방향 이미지
    private int sKind, sNum;                               // 캐릭터의 종류와 번호

    private int pNum, col;                                  // Path 번호와 현재의 경로 배열의 위치
    private int delay, dir, len;                              // 입장시 지연시간, 현재의 방향, 남은 거리
    private int posX, posY;                                // 이동해야 할 목적지(전투 대형) 좌표
    private int aKind;                                         // 공격 경로 번호
    private Random rnd = new Random();             // 난수

    private int diff[] = {7, 4, 2};                         // EASY, MEDIUM, HARD
    private int df;                                             // 게임 난이도


    //--------------------------------
    // 생성자
    //--------------------------------

    public Sprite() {

    }


    //--------------------------------
// Sprite 만들기
//--------------------------------

    // 메서드 설명
    // 1. 자신의 종류와 번호를 보존합니다. 종류는 0 ~ 5 번호는 0~ 7 사이의 값입니다.
    //    캐릭터의 종류와 번호는 MyGrameView에서 m[j].MakeSprite(i, j); 와 같은 형식으로 지정하게 됩니다.
    // 2. 맵의 position 섹션에서 지정한 캐릭터의 이미지 번호를 구한 후 해당 이미지를 비트맵에 읽어 들입니다.
    // 3. 비트맵을 22.5 간격으로 회전시켜 16방향의 이미지를 만들 Canvas를 준비합니다.
    // 4. 캐릭터의 크기와 같은 빈 비트맵을 만듭니다.
    // 5. Canvas에 4의 빈 비트맵을 올려놓습니다.
    // 6. Canvas를 시계방향으로 22.5도 회전합니다.
    // 7. 회전한 Canvas에 원본 이미지를 출력합니다.
    // 8. 캐릭터의 변수를 초기화 시키는 부분으로 앞으로 작성할 메서드입니다.



    public void MakeSprite(int kind, int num) {
        sKind = kind;   //............................................. ①
        sNum = num;

        // 불필요한 캐릭터
        if (MyGameView.mMap.GetSelection(kind, num) == -1) {
            isDead = true;
            return;
        }

        int enemy =  MyGameView.mMap.GetEnemyNum(kind, num);  //........... ②

        Log.e("enemy",enemy+"");

        if(enemy == -1)
            return;

        switch (enemy){
            case 0:
                imgSpt[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.enemy00);
                break;
            case 1:
                imgSpt[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.enemy01);
                break;
            case 2:
                imgSpt[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.enemy02);
                break;
            case 3:
                imgSpt[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.enemy03);
                break;
            case 4:
                imgSpt[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.enemy04);
                break;
            case 5:
                imgSpt[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.enemy05);
                break;

        }


        Log.e("비트맵임당",imgSpt[0]+"");

        int sw = imgSpt[0].getWidth();
        int sh = imgSpt[0].getHeight();
        w = sw / 2;
        h = sh / 2;

        // 16방향으로 회전한 이미지 만들기
        Canvas canvas = new Canvas();      //.............................................. ③
        for (int i = 1; i < 16; i++) {
            imgSpt[i] = Bitmap.createBitmap(sw, sh, Bitmap.Config.ARGB_8888);  //....... ④
            canvas.setBitmap(imgSpt[i]);     //................................................ ⑤
            canvas.rotate(22.5f, w, h);      //.................................................. ⑥
            canvas.drawBitmap(imgSpt[0], 0, 0, null); //................................... ⑦


            Log.e("제대로 만들어짐?",imgSpt[i]+"");
        }

        ResetSprite();        //............. ⑧
    }

    //------------*******************************************************------------------------------//
    //------------*******************************************************------------------------------//
    //------------*******************************************************------------------------------//
    //--------------------------------
    // Reset Sprite
    //--------------------------------

    // (1) : 캐릭터의 종류와 번호는 MyGameView에서 지정하는데, 그것을 MakeSprite() 메서드에서 변수에 저장해두고 필요시에 활용합니다.
    // (1) (2) : 맵에 기록해 뒀던 캐릭터의 시작좌표입니다. 이 값이 -99인 경우 공격 경로로 사용하기로 했는데, 공격 경로의 시작 위치는 캐릭터의 현재의 위치가 됩니다.
    // (4) : 현재의 방향과 이동할 거리를 계산하는 부분입니다. 'col' 이 배열의 첨자(Subscript) 역활을 합니다.
    // (5) : 캐릭터의 이동 속도는 맨 처음에 MapTable()을 작성할때 16방향 삼각함수를 계산해서 배열에 저장해둔 것을 읽어오는 것입니다.


    //------------*******************************************************------------------------------//
    //------------*******************************************************------------------------------//
    public void ResetSprite() {
        // MapTable에서 맵 읽기
        pNum = MyGameView.mMap.GetSelection(sKind, sNum);                             // 자신의 Path 번호  ...... ①
        delay = MyGameView.mMap.GetDelay(sKind, sNum);                                  // Delay 시간 읽기
        shield = MyGameView.mMap.GetShield(sKind, sNum);                                 // 보호막 읽기
        posX = MyGameView.mMap.GetPosX(sKind, sNum);                                  // 전투대형 위치 읽기
        posY = MyGameView.mMap.GetPosY(sKind, sNum);

        GetPath(pNum);                                                                     // pNum으로 구한 Path 읽기
        status = ENTER;                                                                     // 초기 상태는 입장
        isDead = false;
    }



    public void GetPath(int num) {
        sPath = MyGameView.mMap.GetPath(num); // Path 읽기
        // Path의 시작 좌표
        if (sPath.startX != -99)                                                        //.......... ②
        x = sPath.startX;                                                               //.......... ③
        if (sPath.startY != -99)
            y = sPath.startY;
        col = 0;
        GetDir(col);                                                                     //.......... ③
    }



    //--------------------------------
    // GetDir - 현위치의 방향과 거리
    //--------------------------------
    private void GetDir(int col) {                                                     //.......... ④
        dir = sPath.dir[col];                                                          // 이동할 방향
        len = sPath.len[col];                                                           // 이동할 거리
        sx = MyGameView.mMap.sx[dir];                                                   // 이동 속도  .......... ⑤
        sy = MyGameView.mMap.sy[dir];
        imgSprite = imgSpt[dir];                                                      // 현 방향의 이미지

        Log.e("거리오류 검출",dir+"");

    }



    //------------*******************************************************------------------------------//
    //------------*******************************************************------------------------------//
    //---------------------------------Move (캐릭터의 입장) ------------------------------------//
    //------------*******************************************************------------------------------//
    //------------*******************************************************------------------------------//

    public void Move() {
        if (isDead) return;         // 사망자는 자격 없음
        switch (status) {
            case ENTER :             // 캐릭터 입장
                EnterSprite();
                break;
            case BEGINPOS :      // 전투 대형 위치 계산
                break;
            case POSITION :       // 전투 대형 위치로 이동 중
                break;
            case SYNC :            // 전투 대형 위치에서 대기 중
                break;
            case ATTACK :         // 공격 중
                break;
            case BEGINBACK :    // 탈영병 복귀 준비 중
                break;
            case BACKPOS :      // 탈영병 복귀 중
                break;
        }
    }

    //--------------------------------
    // Enter Sprite
    //--------------------------------
    public void EnterSprite() {
        if (--delay >= 0) return;       // delay time이 끝나지 않았으면 대기

        x += (int) (sx * 8);             // 현재의 방향으로 최대 8픽셀 이동
        y += (int) (sy * 8);

        len--;
        if (len >= 0) return;            // 이동할 거리가 남았는가?

        col++;                             // 다음 방향 조사 준비
        if (col < sPath.dir.length) {
            GetDir(col);                // 다음 경로 찾기
        }
        else {
            status = BEGINPOS;    // 경로의 끝이면 전투 대형으로 이동 준비
        }
    }



}
