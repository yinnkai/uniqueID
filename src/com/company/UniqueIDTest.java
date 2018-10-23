package com.company;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/*Tests for some common and special cases. Note: Didn't proof the uniqueness of this function*/
public class UniqueIDTest {

    @Test
    public void testNumber() {
        int[] numToHash = {5325};
        String expected = "ZUXB";
        Encode input = new Encode();
        String res = input.encode(numToHash);
        Assert.assertEquals(expected, res);
    }

    @Test
    public void testIDLength() {
        int[] numToHash = {325982};
        Encode input = new Encode();
        String res = input.encode(numToHash);
        Assert.assertTrue(res.length() >= 4 && res.length() <=6);
    }

    @Test
    public void testIDFirstLetter() {
        int[] numToHash = {9832};
        Encode input = new Encode();
        String res = input.encode(numToHash);
        String vowelNum = "AEIOU0123456789";
        Assert.assertTrue(vowelNum.indexOf(res.charAt(0)) < 0);
    }

    @Test
    public void testSameNum() {
        int[] numToHash1 = {123};
        int[] numToHash2 = {123};
        Encode input = new Encode();
        String res1 = input.encode(numToHash1);
        String res2 = input.encode(numToHash2);
        Assert.assertTrue(res1.equals(res2));
    }

}
