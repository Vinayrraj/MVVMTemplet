package com.vinay.library.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Util {
    private Util() {
    }

    public static String getNonNull(String text) {
        return text != null ? text : "";
    }

    public static String encodeImage(Bitmap bm) {
        int quality = 40;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }
}
