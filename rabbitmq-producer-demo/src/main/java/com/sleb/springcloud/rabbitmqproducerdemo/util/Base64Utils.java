package com.sleb.springcloud.rabbitmqproducerdemo.util;


import com.itextpdf.text.pdf.codec.Base64;



/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/27  9:57
 **/

public class Base64Utils {
    static String params = "范灿$430104198601083022$11$410003136388$2010403000039358$1$-1$-1";

    public static void main(String[] args) {
        try {
            String bytes = Base64.encodeBytes(params.getBytes("UTF-8"), Base64.URL_SAFE);

            System.out.println(bytes);

            System.out.println(new String(Base64.decode(bytes, Base64.URL_SAFE), "UTF-8"));

            System.out.println(new String(java.util.Base64.getUrlDecoder().decode(bytes.replaceAll("\n", "").replaceAll("\r", "")), "UTF-8"));

            System.out.println(new String(org.springframework.util.Base64Utils.decodeFromUrlSafeString(bytes.replaceAll("\n", "").replaceAll("\r", "")), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
