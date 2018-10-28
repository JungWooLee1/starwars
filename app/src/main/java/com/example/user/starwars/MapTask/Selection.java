package com.example.user.starwars.MapTask;

import android.util.Log;

/**
 * Created by user on 2018-06-06.
 */
// -------------------------
// Path의 정류 선택
// -------------------------

public class Selection {

    private int pathNum[][] = new int[6][8] ;
    private int enemyCnt;

    // 생성자 실행 순서

    // ---------------------------------------------------------
    // 1. 작업결과를 저장할 배열을 선언합니다.
    // 2. 매개변수로 넘어 온 문자열을 행 단위로 잘라 배열에 저장합니다.
    // 3. 배열의 첫 번째 요소는 섹션명('selection:' 이므로 이것을 건너뛰고 다음행부터 처리합니다.
    // 4. 1 문자씩 분리합니다.
    // 5. 분리한 문자가 0 ~ 9 이면 문자의 아스키 코드에서 48을 뺍니다 (?)
    // 6. 분리한 문자가 a ~ z 이면 문자의 아스키 코드에서 87을 뻅니다.
    // 7. 외부에서 접근할 수 있도록 Getter 함수를 1개 만듭니다.
    // 8. 전체 적군의 수를 구합니다. 적군의 수는 스테이지를 Clear했는지 판단하는데 사용됩니다.
    // ---------------------------------------------------------

    // 아스키 코드 :
    // 0 ~9 는 아스키코드로 48~58 입니다.
    // a에서 z는 97 ~ 122로 되어있습니다.
    // 따라서 0~9에서 47을 빼면 결과는 0 ~ 9 까지의 값이 되고
    // a-z에서 87을 빼면 결과가 10 ~ 25까지의 값이 됩니다.


    public Selection (String str) {
        String tmp[] = str.split("\n");
        String s;
        enemyCnt =0;
        char ch;

        for (int i =1; i < tmp.length; i++)
        {
            s = tmp[i];

            for (int j = 1; j < 8; j++) {
                ch = s.charAt(j);
                switch (ch) {
                    case '-' :
                        pathNum[i - 1][j] = -1;
                        break;
                    default:
                        enemyCnt ++;
                        if(ch <= '9')
                            pathNum[i - 1][j] = ch - 48;    //0 ~9
                        else
                            pathNum[i - 1][j] = ch - 87;    //'a' ~ 'z'

                }
            }
        }

        Log.v("Selection", "Make Selection success");
    }


    // -----------------------------
    // Path 번호 구하기
    // -----------------------------


    public int GetSelection(int kind, int num) {
        return pathNum[kind][num];
    }

    // -----------------------------
    // 전체 적군의 수 구하기
    // -----------------------------

    public int GetEnemyCount() {
        return enemyCnt;
    }

}












