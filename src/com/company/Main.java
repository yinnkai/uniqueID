package com.company;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.company.Encode;

/**
 * code modified from hashids-java
 * obtained from https://github.com/10cella/hashids-java/blob/v1.0.2/src/main/java/org/hashids/Hashids.java
 * This uniqueID is able to create deterministic unique IDs by given a positive integer.
 */
public class Main {
    public static void main(String args[]) {
        String result = new Encode().scanner();
        System.out.println(result);
    }


}
