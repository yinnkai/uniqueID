package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Encode {
    private static final String alphabet = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    public static int maxNumber = 2147483647;
    public static int minNumber = 1;
    private static final int GUARD_DIV = 12;
    private final int minHashLength = 8;
    private final int salt = 31;
    private String seps = "AEIOU";
    int guardCount = (int) Math.ceil((double) alphabet.length() / GUARD_DIV);
    private String guards = alphabet.substring(0, guardCount);


    public String scanner() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int[] list = new int[1];
        System.out.println("Enter a number: ");
        int num = reader.nextInt();
        if (num > maxNumber || num < minNumber) {
            throw new IllegalArgumentException("Number should be greater than 0 and smaller than " + maxNumber);
        }
        list[0] = num;
        // Scans the next token of the input as an int.
        //once finished
        return encode(list);
    }


    public String encode(int[] batchNumber) {
        if (batchNumber.length == 0) {
            return "";
        }

        for (long number : batchNumber) {
            if (number < 0) {
                return "";
            }
            if (number > maxNumber) {
                throw new IllegalArgumentException("number can not be greater than " + maxNumber);
            }
        }

        int numberHash = 0;

        for (int i = 0; i < batchNumber.length; i++) {
            numberHash += (batchNumber[i] % (i + 100));
        }
        String alphabet = this.alphabet;
        char ret = alphabet.charAt((int) (numberHash % alphabet.length()));
        // char lottery = ret;
        int num;
        int sepsIndex, guardIndex;
        String buffer;
        StringBuilder ret_strB = new StringBuilder(minHashLength);
        ret_strB.append(ret);
        char guard;
        for (int i = 0; i < batchNumber.length; i++) {
            num = batchNumber[i];
            buffer = ret + this.salt + alphabet;

            alphabet = Encode.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
            String last = Encode.hash(num, alphabet);

            ret_strB.append(last);

            if (i + 1 < batchNumber.length) {
                if (last.length() > 0) {
                    num %= ((int) last.charAt(0) + i);
                    sepsIndex = (int) (num % this.seps.length());
                } else {
                    sepsIndex = 0;
                }
                ret_strB.append(this.seps.charAt(sepsIndex));
            }
        }

        String ret_str = ret_strB.toString();
        if (ret_str.length() < this.minHashLength) {
            guardIndex = (numberHash + (int) (ret_str.charAt(0))) % this.guards.length();
            guard = this.guards.charAt(guardIndex);

            ret_str = guard + ret_str;

            if (ret_str.length() < this.minHashLength) {
                guardIndex = (numberHash + (int) (ret_str.charAt(2))) % this.guards.length();
                guard = this.guards.charAt( guardIndex);

                ret_str += guard;
            }
        }

        int halfLen = alphabet.length() / 2;
        while (ret_str.length() < this.minHashLength) {
            alphabet = Encode.consistentShuffle(alphabet, alphabet);
            ret_str = alphabet.substring(halfLen) + ret_str + alphabet.substring(0, halfLen);
            int excess = ret_str.length() - this.minHashLength;
            if (excess > 0) {
                int start_pos = excess / 2;
                ret_str = ret_str.substring(start_pos, start_pos + this.minHashLength);
            }
        }

        return ret_str;
    }

    private static String consistentShuffle(String alphabet, String salt) {
        if (salt.length() <= 0) {
            return alphabet;
        }

        int asc_val, j;
        char[] tmpArr = alphabet.toCharArray();
        for (int i = tmpArr.length - 1, v = 0, p = 0; i > 0; i--, v++) {
            v %= salt.length();
            asc_val = (int) salt.charAt(v);
            p += asc_val;
            j = (asc_val + v + p) % i;
            char tmp = tmpArr[j];
            tmpArr[j] = tmpArr[i];
            tmpArr[i] = tmp;
        }

        return new String(tmpArr);
    }

    private static String hash(int input, String alphabet) {
        String hash = "";
        int alphabetLen = alphabet.length();

        do {
            int index = (int) (input % alphabetLen);
            if (index >= 0 && index < alphabet.length()) {
                hash = alphabet.charAt(index) + hash;
            }
            input /= alphabetLen;
        } while (input > 0);


        return hash;
    }
}
