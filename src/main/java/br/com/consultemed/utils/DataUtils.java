package br.com.consultemed.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {

    public static String formatarData(Date data) {
        return formatarData(data, "dd/MM/yyyy");
    }

    public static String formatarData(Date data, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(data);
    }

    public static Date stringToDate(String stringData) throws ParseException {
        return stringToDate(stringData, "dd/MM/yyyy");
    }


    public static Date stringToDate(String stringData, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(stringData);
    }
}
