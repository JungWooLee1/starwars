package com.example.user.starwars.MapTask;

/**
 * Created by user on 2018-06-09.
 */

public class SinglePath {

    public int startX;
    public int startY;

    public int dir[]; // 경로 방향
    public int len[]; // 경로 거리리

    // 1. 매개변수로 넘어온 str을 :을 기준으로 잘라 배열로 만듭니다.
    // 2. 경로의 시작 좌표를 구합니다.
    // 3. 경로를 ','를 기준으로 나누어 배열에 저장합니다.
    // 4. 배열의 크기를 구합니다. 이 값이 경로의 갯수입니다.
    // 5. 변수 선언부에서 만들어 둔 배열의 크기를 설정합니다.

    public SinglePath(String str) {

        String tmp[] = str.split(":"); // (1)

        int n = tmp[1].indexOf(",");
        startX = Integer.parseInt(tmp[1].substring(0, n).trim());   // (2)
        startY = Integer.parseInt(tmp[1].substring(n+1).trim());


        String stmp[] = tmp[2].split(",");          // (3)
        n = stmp.length;                                   // (4)
        dir = new int[n];                                 // (5)
        len = new int[n];


        int p;
        for (int i = 0; i < n; i++) {
            p = stmp[i].indexOf("-");                   //(6)
            dir[i] = Integer.parseInt(stmp[i].substring(0, p).trim());
            len[i] = Integer.parseInt(stmp[i].substring(p+1).trim());
        }




    }
}
