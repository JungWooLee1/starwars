package com.example.user.starwars;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    MyGameView mGameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Log.e("[0]","0000000000000");

        mGameView = (MyGameView) findViewById(R.id.mGameView);


        Log.e("[0.1]","00000000000001111");
    }


    //-------------------------------------
    //  Option Menu
    //-------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        Log.e("[1]","111");
        Log.e("[1]","111");
        Log.e("[1]","111");

        menu.add(0, 1, 0, "Quit Game");
        menu.add(0, 2, 0, "Pause Game");
        menu.add(0, 3, 0, "Resume Game");
        menu.add(0, 4, 0, "Music On");
        menu.add(0, 5, 0, "Sound On");
        menu.add(0, 6, 0, "Vibrator On");


        return true;
    }

    //-------------------------------------
    //  onOptions ItemSelected
    //-------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 1:     // QuitGame
                MyGameView.StopGame();        // ①
                finish();
                break;
            case 2:      // PauseGame
                MyGameView.PauseGame();       // ②
                break;
            case 3:      // Resume
                MyGameView.ResumeGame();     // ③
                break;
            case 4:      // Music On/off
                break;
            case 5:       // Sound On/Off
                break;
            case 6:       // Vibrator On/Off
        }
        return true;
    }


}