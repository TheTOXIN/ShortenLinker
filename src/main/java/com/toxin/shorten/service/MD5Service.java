package com.toxin.shorten.service;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.stream.Stream;

@Service
public class MD5Service {

    private static final int nA = 0;
    private static final int nB = 1;
    private static final int nC = 2;
    private static final int nD = 3;

    private static final byte ONE = 0x8;

    private static final int BIT_IN_BYTE = 8;
    private static final int BLOCK_SIZE = 512;
    private static final int SUB_SIZE = 64;
    private static final int COUNT_WORD = 16;
    private static final int WORD_SIZE = 4;
    private static final int COUNT_ROUND = 4;
    private static final int T_SIZE = BLOCK_SIZE / BIT_IN_BYTE;
    private static final int POW = T_SIZE / 2;

    public String hash(String data) {

        //STEP 1

        byte[] bytes = data.getBytes();
        bytes = ArrayUtils.add(bytes, ONE);

        int L = bytes.length * 8;
        int dL = 0;
        int N = 0;

        while (dL < L) {
            dL = BLOCK_SIZE * N + BLOCK_SIZE - SUB_SIZE;
            N++;
        }

        int countNil = (dL - L) / (SUB_SIZE / BIT_IN_BYTE);
        bytes = ArrayUtils.addAll(bytes, new byte[countNil]);

        //STEP2

        byte[] subData = ArrayUtils.subarray(
            data.getBytes(),
            0,
            SUB_SIZE / BIT_IN_BYTE
        );

        bytes = ArrayUtils.addAll(bytes, subData);

        //STEP3

        int A = 0x01234567;
        int B = 0x89ABCDEF;
        int C = 0xFEDCBA98;
        int D = 0x76543210;

        int[] ABCD = {A, B, C, D};

        int[] T = new int[T_SIZE];

        for (int i = 0; i < T.length; i++) {
            T[i] = (int) (Math.pow(2, POW) * Math.abs(Math.sin(i)));
        }

        int[][] S = {
            {7, 12, 17, 22},
            {5, 9, 14, 20},
            {4, 11, 16, 23},
            {6, 10, 15, 21}
        };

        FUN[] funs = {
            (X, Y, Z) -> (X & Y) | (~X & Z),
            (X, Y, Z) -> (X & Z) | (~Z & Y),
            (X, Y, Z) -> (X ^ Y ^ Z),
            (X, Y, Z) -> (Y ^ (~Z | X))
        };

        //STEP4

        for (int b = 0; b < bytes.length; b += BLOCK_SIZE / BIT_IN_BYTE) {
            byte[] block = ArrayUtils.subarray(bytes, b, b + BLOCK_SIZE / BIT_IN_BYTE);
            int[] X = new int[COUNT_WORD];

            for (int w = 0; w < X.length; w++) {
                byte[] word = ArrayUtils.subarray(block, w * WORD_SIZE, w * WORD_SIZE + WORD_SIZE);
                X[w] = new BigInteger(word).intValue();
            }

            int[] ABCDtmp = ArrayUtils.clone(ABCD);

            for (int i = 0; i < T_SIZE; i++) {
                int round = i >>> COUNT_ROUND;
                int cycle = i % COUNT_ROUND;

                for (int j = 0; j < X.length; j++) {
                    ABCD[cycle] = compute(ABCD, funs[round], X[j], T[i], S[round][cycle]);
                }
            }

            for (int i = 0; i < COUNT_ROUND; i++) {
                ABCD[i] += ABCDtmp[i];
            }
        }

        //STEP5

        String hash = Stream.of(ABCD[nA], ABCD[nB], ABCD[nC], ABCD[nD])
            .map(BigInteger::valueOf)
            .map(BigInteger::toByteArray)
            .map(HexBin::encode)
            .reduce((s1, s2) -> s1 + s2)
            .orElse("");

        return hash;
    }

    private int compute(int[] ABCD, FUN f, int X, int T, int S) {
        return ABCD[nB] + (ABCD[nA] + f.fun(ABCD[nB], ABCD[nC], ABCD[nD]) + X + T << S);
    }

    @FunctionalInterface
    private interface FUN {
        int fun(int X, int Y, int Z);
    }

}
