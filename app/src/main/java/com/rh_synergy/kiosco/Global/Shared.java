package com.rh_synergy.kiosco.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by PC-108 on 3/31/2016.
 */
public class Shared {

    /*
    * Set currency format to String
    * */
    public static String getMoneyFormat(float number){
        Locale locale = new Locale("en", "US");
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locale);
        return moneyFormat.format(number);
    }

    /*
    * Get the day on Spanish
    * */
    public static String getSpanishDay(int dia){
        switch (dia)
        {
            case 1:
                return "Lunes";
            case 2:
                return "Martes";
            case 3:
                return "Miercoles";
            case 4:
                return "Jueves";
            case 5:
                return "Viernes";
            case 6:
                return "Sábado";
            default:
                return "Domingo";
        }
    }

    /*
    * Get spanish format of date Sample: (11/Marzo/2016)
    * */
    public static String getSpanishDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return  day + "/" + getMonthNameSpanish(month) + "/" + year;
    }

    /*
    * Get the name from month in spanish
    * */
    public static String getMonthNameSpanish(int month){
        switch (month)
        {
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            default:
                return "Diciembre";
        }
    }

    /*
    * Get the date on Spanish Format "dd/MM/yyyy"
    * */
    public static String getDateFormatSpanish(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String dayformatted = ("00" + Integer.toString(day)).substring(Integer.toString(day).length());
        String monthformatted = ("00" + Integer.toString(month)).substring(Integer.toString(month).length());

        return dayformatted + "/" + monthformatted + "/" + year;
    }

    /*
    * To check if server is reachable
    * */
    public static String getURL()
    {
        try {
            InetAddress.getByName("http://192.168.7.22:8081/api/").isReachable(3000); //Replace with your name
            return "http://192.168.7.22:8081/api/";
        } catch (Exception e) {
            return "http://192.168.7.3:8081/api/";
        }
    }


    /*
    * Lee el json de la Web Api Kiosco
    * Abrir la coneccion para acceso JSON (Web Api Kiosco)
    * */
    public static String readJSONFeed(final int NoEmpl, String URL) {
        HttpURLConnection c = null;
        try {
            java.net.URL u = new URL(URL);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new
                            InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
                case 404:
                    return "Error";
            }
        } catch (MalformedURLException ex) {
            return "Error";
        } catch (IOException ex) {
            return "Error";
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    return "Error";
                }
            }
        }
        return null;
    }
}
