package com.example.user.starwars.MapTask;

import android.util.Log;

import com.example.user.starwars.MyGameView;
import com.example.user.starwars.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 2018-06-03.
 */

public class MapTable {

    public float sx[] = {0f, 0.39f, 0.75f, 0.93f, 1, 0.93f, 0.75f, 0.39f, 0f, -0.39f, -0.75f, -0.93f, -1f, -0.93f, -0.75f, -0.39f};
    public float sy[] = {-1f, -0.93f, -0.75f, -0.39f, 0f, 0.39f, 0.75f, 0.93f, 1f, 0.93f, 0.75f, 0.39f, 0f, -0.39f, -0.75f, -0.93f};


    private Path mPath;
    private Selection mSelect;

    private DelayTime mDelay;            // Delay
    private Position mPos;                 // Pisition
    private Shield mShield;                 // Shield


    public int enemyCnt;                    // 현스테이지에서 적군의 생존자 수
    public int attackTime;                   // 적군의 공격 시작 시간


    public void MapTable() {

    }

    // 리소스 파일을 읽어옵니다
    // 리소스 파일을 섹션별로 분리해서 맵을 만드는 부분입니다.

    public void ReadMap(int num) {


        num -- ;
        // 스테이지에 해당되는 맵 파일 읽기
        InputStream fi =  MyGameView.mContext.getResources().openRawResource(R.raw.stage01 + num );


        Log.e("stage + 1", R.raw.stage01+"");
        Log.e("stage + 1",(R.raw.stage01+1) +"");

        try {
            byte [] data  =  new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s =  new String(data, "EUC-KR");
            MakeMap(s);

        } catch(IOException e) {

        }

    }


    // 1. 읽어들인 파일에서 selection 문자열을 찾습니다.
    // 2. 찾은 문자열 직전까지 잘라서 Path() class에 전달하고 그 결과를 변수에 담습니다.

    public void MakeMap(String str) {

        int n1 = str.indexOf("selection");
        mPath =  new Path(str.substring(0, n1));    // path

        int n2 = str.indexOf("delay");
        mSelect = new Selection(str.substring(n1, n2));


        enemyCnt = mSelect.GetEnemyCount();                // 적군의 수 .... ①

        n1 = str.indexOf("position");
        mDelay = new DelayTime(str.substring(n2, n1));       // Delay
        attackTime = mDelay.GetDelay(0, 5);                     // 마지막 캐릭터  ... ②

        n2 = str.indexOf("shield");
        mPos = new Position(str.substring(n1, n2));             // Position
        mShield = new Shield(str.substring(n2));

    }

    // 외부에서 경로를 구할수 있도록 해주는 Getter 함수입니다.
    public SinglePath GetPath(int num) {
        return mPath.GetPath(num);
    }


    //--------------------------------------
    //  Get Delay
    //--------------------------------------
    public int GetDelay(int kind, int num) {
        return mDelay.GetDelay(kind, num);
    }

    //--------------------------------------
    //  Get Selection
    //--------------------------------------
    public int GetSelection(int kind, int num) {
        return mSelect.GetSelection(kind, num);
    }

    //--------------------------------------
    //  Get X position
    //--------------------------------------
    public int GetPosX(int kind, int num) {
        return mPos.GetPosX(kind, num);
    }

    //--------------------------------------
    //  Get Y position
    //--------------------------------------
    public int GetPosY(int kind, int num) {
        return mPos.GetPosY(kind, num);
    }

    //--------------------------------------
    //  Get Enemy Num
    //--------------------------------------
    public int GetEnemyNum(int kind, int num) {
        return mPos.GetEnemyNum(kind, num);
    }

    //--------------------------------------
    //  Get Shield
    //--------------------------------------
    public int GetShield(int kind, int num) {
        return mShield.GetShield(kind, num);
    }
}
