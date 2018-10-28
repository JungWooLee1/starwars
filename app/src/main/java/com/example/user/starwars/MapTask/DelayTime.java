package com.example.user.starwars.MapTask;

import android.util.Log;

/**
 * Created by user on 2018-06-06.
 */

public class DelayTime {

    private int Delay[][] = new int[6][8];

    // -----------------------------
    // 생성자
    // -----------------------------

    // 1. 매개변수를 행 단위로 나눕니다.
    // 2. 각 행의 문자열을 4문자씩 잘라서 좌우의 공백을 제거합니다.

    public DelayTime(String str) {
        String tmp[] = str.split("\n");
        String s;
        int n;

        int debug = tmp.length;
        for(int i =1; i < tmp.length; i++) {
            for(int j = 0; j < 8; j++) {

                String tmp_ = tmp[i] + " ";
                s = tmp_.substring(j * 4, (j + 1) * 4).trim();
                if (s.equals("---"))
                    Delay[i - 1][j] = -1;
                else
                    Delay[i - 1][j] = Integer.parseInt(s);
            }
        }

        Log.v("Delay Success", "Success");


        // -----------------------------
        // Get Delay
        // -----------------------------

    }

    public int GetDelay(int kind, int num) {
        return Delay[kind][num];
    }


}
