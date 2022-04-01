package com.shazyar.app;

import com.shazyar.convert.NumberToArabicWords;
import com.shazyar.convert.NumberToEnglishWords;
import com.shazyar.convert.NumberToKurdishWords;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class MainApp {

    public static void main(String[] args) {

        // For Kurdish Language
        print(NumberToKurdishWords.convert(200));
        // For English Language
        System.out.println(NumberToEnglishWords.convert(10893));
        // For Arabic Language
        print(NumberToArabicWords.convert(10893));

    }

    public static void print(String text){
        try{
            PrintStream outStream = new PrintStream(System.out, true, "UTF-8");
            outStream.println(text);
        } catch(UnsupportedEncodingException e){
            System.out.println("Caught exception: " + e.getMessage());
        }
    }


}
