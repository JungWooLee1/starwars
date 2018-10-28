package com.example.user.starwars.MapTask;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 2018-06-03.
 */

public class Path {

    private ArrayList <SinglePath> mPath = new ArrayList<SinglePath>();


    // 1. 전체 문자를 개행문자 ("\n")을 기준으로 나누어 행단위로 배열에 저장합니다.
    // 2. 행 단위로 SinglePath() 로 전달하고 SinglePath를 ArrayList에 순서대로 저장합니다.
    // 3. 맵을 만드는 과정에서 에러가 발생할 경우 그 위치를 찾기가 곤란하므로 군데군데 로그를 출력해서 확인합니다.


    // 여기서 str은 Map table 에서 전달받는 값입니다.
    public  Path(String str) {

        String tmp[] = str.split("\n");

        for (int i = 0; i < tmp.length; i++) {

            // 주석문은 모두 무시합니다.
            if (tmp[i].indexOf("//") >= 0 || tmp[i].trim().equals(""))
                continue;

            mPath.add(new SinglePath(tmp[i]));
        }

        Log.v("Path", "Make path success");

    }

    public SinglePath GetPath(int index) {
        return mPath.get(index);
    }
}


// 전달받은 문자열을 1개 경로의 문저열로 나눠주는 클래스입니다.

