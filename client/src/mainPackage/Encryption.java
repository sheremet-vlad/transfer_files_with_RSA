package mainPackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Encryption {
    //open and secret keys for RSA
    private static final int P = 11;
    private static final int Q = 13;
    private static final int R = P * Q;
    private static final int FI = (P - 1) * (Q - 1);
    private static final int E = 7;
    private static final int D = euclid(FI,E);

    public static void makeEncryption(byte[] buffer){
        int temp;
        for (int i = 0; i < buffer.length; i++) {
            temp = fastEXP(buffer[i],E,R);
            buffer[i] = (byte)temp;

        }
    }

    public static int fastEXP (int a, int z, int n){
        int a1 = a;
        int z1 = z;
        int x = 1;
        while (z1 != 0){
            while (z1 % 2 == 0) {
                z1 = z1 / 2;
                a1 = (a1 * a1) % n;
            }
            z1--;
            x = (x * a1) % n;
        }
        return x;
    }

    public static int euclid(int fi, int e){
        int d0 = e;
        int d1 = fi;
        int x0 = 1;
        int y0 = 0;
        int x1 = 0;
        int y1 = 1;

        int q,d2,x2,y2;

        while (d1 > 1) {
            q = d0 / d1;
            d2 = d0 % d1;
            x2 = x0 - q*x1;
            y2 = y0 - q*y1;
            d0 = d1;
            d1 = d2;
            x0 = x1;
            x1 = x2;
            y0 = y1;
            y1 = y2;
        }

        if (y1 < 0){
            y1 += fi;
        }

        return y1;
    }

}
