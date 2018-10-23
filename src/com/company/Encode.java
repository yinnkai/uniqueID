package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* author: Yinkai(Kate)*/

public class Encode {
    private static final String alphabet = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    private static final String firstChar = new String("BCDFGHJKLMNPQRSTVWXYZ");
    private static int maxNumber = 2147483647;
    private static int minNumber = 1;
    private static final int GUARD_DIV = 12;
    private final int minHashLength = 4;
    private final int maxHashLength = 6;
    private final int salt = 31;
    private String seps = "AEIOU";
    private int guardCount = (int) Math.ceil((double) alphabet.length() / GUARD_DIV);
    private String guards = alphabet.substring(0, guardCount);

    /** Get inputs from systemin **/
    public String scanner() {
        Scanner reader = new Scanner(System.in); //Reading from System.in
        int[] list = new int[1];
        System.out.println("Enter a number: ");
        if (reader.hasNextInt()) {
            int num = reader.nextInt(); // Scans the next token of the input as an int.
            if (num > maxNumber || num < minNumber) {
                throw new IllegalArgumentException("Please input a positive integer that greater than 0 and smaller than " + maxNumber);
            }
            list[0] = num;
        } else {
            throw new NumberFormatException("Invalid input, should be a number");
        }
        return encode(list);
    }

    /**
     * Encrypt input number to string
     *
     * @param batchNumber to encrypt
     * @return the encrypt string
     */
    public String encode(int[] batchNumber) {
        if (batchNumber.length == 0) { // check whether the input is null or invalid
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
        String firstChar = this.firstChar;
        char ret = alphabet.charAt((int) (numberHash % alphabet.length()));
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

        /* recalculate the first letter of ID if it is vowel or number*/
        if (firstChar.indexOf(ret_strB.charAt(0)) < 0) {
            num = batchNumber[0];
            buffer = ret + this.salt + firstChar;
            firstChar = Encode.consistentShuffle(firstChar, buffer.substring(0, firstChar.length()));
            String first = Encode.hash(num, firstChar);
            ret_strB.replace(0,1,first);
        }

        /*convert the stringbuilder to string and make sure the generated ID is within size limitation.*/
        String ret_str = ret_strB.toString();
        if (ret_str.length() < this.minHashLength) {
            guardIndex = (numberHash + (int) (ret_str.charAt(0))) % this.guards.length();
            guard = this.guards.charAt(guardIndex);

            ret_str = ret_str + guard;

            if (ret_str.length() < this.minHashLength) {
                guardIndex = (numberHash + (int) (ret_str.charAt(2))) % this.guards.length();
                guard = this.guards.charAt( guardIndex);

                ret_str = ret_str + guard;
            }
        }

        int halfLen = firstChar.length() / 2;
        while (ret_str.length() < this.minHashLength) {
            firstChar = Encode.consistentShuffle(firstChar, firstChar);
            ret_str = firstChar.substring(halfLen) + ret_str + firstChar.substring(0, halfLen);
            int excess = ret_str.length() - this.minHashLength;
            if (excess > 0) {
                int start_pos = excess / 2;
                ret_str = ret_str.substring(start_pos, start_pos + this.minHashLength);
            }
        }

        if (ret_str.length() > this.maxHashLength) {
            int excess = ret_str.length() - this.maxHashLength;
            ret_str = ret_str.substring(0,ret_str.length() - excess);
        }

        return ret_str;
    }

    /**
     * shuffle the given alphabet consistently to make the generated ID irregular and hard to be guessed.
     *
     * @param alphabet, string of characters that shouldn't be arrange together
     * @return shuffled string
     */
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

    /**
     * create hash code that used to convert into the last character of a temp stringbuilder.
     *
     * @param input batchNumber, alphabet that used to pick characters from
     * @return UniqueID string
     */
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
