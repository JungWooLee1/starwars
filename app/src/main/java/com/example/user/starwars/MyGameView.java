package com.example.user.starwars;

/**
 * Created by user on 2018-05-31.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.user.starwars.MapTask.MapTable;

// MyGameView는 Surfaceview를 상속받습니다.
// SurfaceHolder의 Callback 함수를 구현합니다.




public class MyGameView extends SurfaceView implements Callback {

    static GameThread mThread;                         // GameThread
    SurfaceHolder mHolder;                        // SurfaceHolder
    public static Context mContext;                              // Context


    static MapTable mMap;              // 전역 변수 선언

    // GameView의 생성자입니다.
    // SurfaceHolder을 초기화하고 callback함수를 등록합니다.
    // GameThread를 초기화 합니다.
    // 마지막으로 게임을 실행시키고 스테이지를 생성합니다.


    /////////////////////////////////////////////////////////
    // 게임에 필요한 static 값들을 정의합니다.
    /////////////////////////////////////////////////////////

    static int stageNum = 1;                                     //...................... ①
    static int Width, Height;
    static Sprite mEnemy[][] = new Sprite[6][8];                // 적군
    static Bitmap imgBack;                                              // 배경 이미지
    static int sw[] = new int[6];                                       // 적군의 폭과 높이
    static int sh[] = new int[6];

    public MyGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mHolder = holder;                                            // holder와 Context 보존
        mContext = context;
        mThread = new GameThread(holder, context);      // Thread 만들기

        InitGame();                                                   // 게임 초기화

        MakeStage();                                               // 스테이지 만들기
        setFocusable(true);     // View가 Focus받기




    }

    //-------------------------------------
    //  InitGame
    //-------------------------------------
    private void InitGame() {
        mMap = new MapTable();           // InitGame()에서 입력


        //Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();          //.......... ②
        //Width = display.getWidth();
        //Height = display.getHeight();


        DisplayMetrics disp = mContext.getResources().getDisplayMetrics();                                   //.......... ②
        Width = disp.widthPixels;       //화면의 가로폭
        Height = disp.heightPixels;     //화면의 세로폭

        // 적군 캐릭터 배열
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                mEnemy[i][j] = new Sprite();
            }
        }

    }

    //-------------------------------------
    //  MakeStage
    //-------------------------------------
    public static void MakeStage() {

        mMap.ReadMap(1);       // MakeStage()에서 입력




        imgBack = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.space0 + stageNum % 5 - 1);
        imgBack = Bitmap.createScaledBitmap(imgBack, Width, Height, true);

        // 배열에 적군 캐릭터 만들기
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                mEnemy[i][j].MakeSprite(i, j);
            }
            sw[i] = mEnemy[i][2].w;             // 적군의 폭 (실제로는 폭의 절반)
            sh[i] = mEnemy[i][2].h;             // 적군의 높이 (실제로는 높이의 절반)
        }

    }

    //-------------------------------------
    //  SurfaceView가 생성될 때 실행되는 부분
    //  SurfaceView가 생성되면 Thread를 시작시켜줍니다.
    //-------------------------------------
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.start();                           // Thread 실행
    }

    //-------------------------------------
    //  SurfaceView가 바뀔 때 실행되는 부분입니다.
    //-------------------------------------
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {

    }

    //-------------------------------------
    //  SurfaceView가 해제될 때 실행되는 부분
    //-------------------------------------
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

//----------------------------------------------------------------

    //-------------------------------------
    //  GameThread Class
    //-------------------------------------
    class GameThread extends Thread {
        boolean canRun = true;   // Thread 제어용
        boolean isWait = false;   // Thread 제어용

        //-------------------------------------
        //  생성자
        //-------------------------------------
        public GameThread(SurfaceHolder holder, Context context) {

        }

        //-------------------------------------
        //  Move All
        //-------------------------------------
        public void MoveAll() {


            Log.e("적군 이동","실행");
            // 적군        .......... ④
            for (int i = 5; i >= 0; i--) {
                for (int j = 0; j < 8; j++)
                    mEnemy[i][j].Move();
            }

        }

        //-------------------------------------
        //  DrawAll
        //-------------------------------------

        // 1. canvas에 bitmap을 그려줍니다.
        // 2. i : 캐릭터 종류  ; j : 캐릭터 번호 ( 행단위 )
        // 3. 캐릭터가 죽지 않았다면(isDead) 적군에 대한 bitmap 이미지를 그려줍니다.
        // 4. 이미지를 그릴때는 drawBitmap을 사용하는데 첫번째 값이 비트맵 이미지값입니다.



        // 캔버스에 파싱한 데이터를 기준으로 그림을 그려주는 메서드입니다.
        public void DrawAll(Canvas canvas) {
            // 배경화면    ............. ⑤

            Log.e("전체 이동 이동","실행");

            canvas.drawBitmap(imgBack, 0, 0, null);



            // 적군
            // 적구
            for (int i = 5; i >= 0; i--) {
                for (int j = 0; j < 8; j++) {

                    if (mEnemy[i][j].isDead) continue;
                    try {
                        canvas.drawBitmap(mEnemy[i][j].imgSprite, mEnemy[i][j].x - sw[i], mEnemy[i][j].y - sh[i], null);
                    }catch (Exception e) {

                    }
                }
            }
        }

        //-------------------------------------
        //  스레드 본체입니다.
        //  canvas가 변형될때마다 synchronized 를 통해 각각의 scene을 동기화합니다.
        //  scene이 모두 그려지면 canvas를 unlock한 뒤에 다시 while 루프를 실행합니다.
        //-------------------------------------
        public void run() {
            Canvas canvas = null;
            while (canRun) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        MoveAll();                                 // 모든 캐릭터 이동
                        DrawAll(canvas);                        // Canvas에 그리기
                    } // sync
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                } // try


                synchronized (this) {
                    if (isWait)             // Pause 모드이면
                        try {
                            wait();       // 스레드 대기
                        } catch (Exception e) {
                            // nothing
                        }
                } // sync
            } // while
        } // run


        //-------------------------------------
        //  스레드 완전 정지
        //-------------------------------------
        public void StopThread() {
            canRun = false;
            synchronized (this) {
                this.notify();                   // 스레드에 통지
            }
        }

        //-------------------------------------
        //  스레드 일시정지 / 재기동
        //-------------------------------------
        public void PauseNResume(boolean value) {
            isWait = value;
            synchronized (this) {
                this.notify();               // 스레드에 통지
            }
        }
    } // GameThread 끝




    //-------------------------------------
    //  스레드 완전 정지
    //-------------------------------------
    public static void StopGame() {
        mThread.StopThread();
    }

    //-------------------------------------
    //  스레드 일시 정지
    //-------------------------------------
    public static void PauseGame() {
        mThread.PauseNResume(true);
    }

    //-------------------------------------
    //  스레드 재기동
    //-------------------------------------
    public static void ResumeGame() {
        mThread.PauseNResume(false);
    }

    //-------------------------------------
    //  게임 초기화
    //-------------------------------------
    public void RestartGame() {
        mThread.StopThread();  // 스레드 중지
        // 현재의 스레드를 비우고 다시 생성
        mThread = null;
        mThread = new GameThread(mHolder, mContext);
        mThread.start();
    }

} // SurfaceView