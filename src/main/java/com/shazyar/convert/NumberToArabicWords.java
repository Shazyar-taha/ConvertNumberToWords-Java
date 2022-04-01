package com.shazyar.convert;

import java.text.DecimalFormat;

public class NumberToArabicWords {

    private static final String[] numNames = {"", "واحد", "اثنان", "ثلاثة",
            "أربعة", "خمسة", "ستة", "سبعة", "ثامنية", "تعسة", "عشرة", "احد عشر", "اثنا عشر",
            "ثلاثة عشر", "‌أربعة عشر", "خمسة عشر", "ستة عشر", "سبعة عشر", "ثمانیة عشر", "تسعة عشر"};

    private static final String[] tensNames = {"", "عشرة", "عشرون", "ثلاثون",
            "أربعون", "خمسون",
            "ستون", "سبعون", "ثمانون", "تعسون"};

    private static final String[] houndredNames = {"", "مئة", "مائتان", "ثلاثمئة", "اربعمئة",
            "خمسمئة", "ستمئة", "سبعمئة", "ثمانیمئة", "تسعمئة"};

    public NumberToArabicWords() {

    }

    //this is the main work
    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) {
            return "سفر";
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        // bo Bilion
        String tradBillions;
        if (billions == 0) {
            tradBillions = "";
        } else {
            switch (billions) {
                case 1:
                    tradBillions = setAnd(Long.parseLong(snumber.substring(0, 12)), 1000000000, " ملیار ")
                            + convertLessThanOneThousand(billions);
                    break;
                case 2:
                    tradBillions = setAnd(Long.parseLong(snumber.substring(0, 12)), 1000000000, " ملیارین ");
                    break;
                default:
                    tradBillions = convertLessThanOneThousand(billions)
                            + setAnd(Long.parseLong(snumber.substring(0, 12)), 1000000000, " ملیارین ");

            }

        }
        String result = tradBillions;

        //bo Milion
        String tradMillions;
        if (millions == 0) {
            tradMillions = "";
        } else {
            switch (millions) {
                case 1:
                    tradMillions = setAnd(Integer.parseInt(snumber.substring(3, 12)), 1000000, " ملیون ")
                            + convertLessThanOneThousand(millions);
                    break;
                case 2:
                    tradMillions = setAnd(Integer.parseInt(snumber.substring(3, 12)), 1000000, " ملیونان ");
                    break;
                default:
                    tradMillions = convertLessThanOneThousand(millions)
                            + setAnd(Integer.parseInt(snumber.substring(3, 12)), 1000000, " ملایین ");

            }

        }
        result = result + tradMillions;

        String tradHundredThousands;

        if (hundredThousands == 0) {
            tradHundredThousands = "";
        } else {
            //bo away agar 1000 bw ble alaf agar 2000 bw ble alfen etr baqi tr ble alaf
            switch (hundredThousands) {
                case 1:
                    tradHundredThousands = setAnd(Integer.parseInt(snumber.substring(6, 12)), 1000, " الف ");
                    break;
                case 2:
                    tradHundredThousands = setAnd(Integer.parseInt(snumber.substring(6, 12)), 1000, " الفین");
                    break;
                default:
                    tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                            + setAnd(Integer.parseInt(snumber.substring(6, 12)), 1000, " الاف");
                    break;
            }

        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    private static String convertLessThanOneThousand(int number) {
        String soFar;
        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            //ama leraya bo away agar mody 10 krd krdia sfr nache (و) dabnet
            if (number % 10 == 0) {

                number /= 10;

                soFar = tensNames[number % 10];
                number /= 10;
            } else {
                soFar = numNames[number % 10];
                number /= 10;

                String tenName = tensNames[number % 10];

                soFar = soFar + " و " + tenName;

                number /= 10;
            }

        }
        if (number == 0) {
            return soFar;
        }

        return soFar.equals("") ? houndredNames[number] + soFar : houndredNames[number] + " و " + soFar;

    }

    //bo danany (و) la newan zhmarakan
    private static String setAnd(long number, long mod, String text) {
        return (number % mod != 0) ? text + " و " : text;
    }
}
