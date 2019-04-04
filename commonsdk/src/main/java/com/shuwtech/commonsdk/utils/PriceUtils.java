package com.shuwtech.commonsdk.utils;

import java.text.DecimalFormat;

public class PriceUtils {

    public static String formatPrice(double price) {
        return new DecimalFormat("0.00").format(price);
    }

    public static String formatPrice(String price) {
        if (price == null) return "0.00";

        try {
            return new DecimalFormat("0.00").format(Double.parseDouble(price));
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    public static String formatPriceInt(double price) {
        return new DecimalFormat("#").format(price);
    }
}