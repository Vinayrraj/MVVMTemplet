/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vinay.library.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    public static final String DATE_TIME_PATTERN_SERVER = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_TIME_PATTERN_HOME = "yyyy/MM/dd 'at' hh:mm a";
    public static final String DATE_TIME_PATTERN_HOME_REFRESH = "MM/dd 'at' hh:mm a";
    public static final String DATE_TIME_LOCATION_API = "yyyy-MM-dd HH:mm:ss";

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commit();
    }

    public static String getStringFromRaw(final Context context, @RawRes int id) {
        InputStream is = context.getResources().openRawResource(id);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            return writer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String getAppVersionNameAndrCode(final Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return "v" + pInfo.versionName + "(" + pInfo.versionCode + ")";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date parseDate(String dateString, String pattern, TimeZone targetTimeZone) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if (targetTimeZone != null)
            simpleDateFormat.setTimeZone(targetTimeZone);
        return simpleDateFormat.parse(dateString);
    }

    public static String formatDate(Date date, String pattern, TimeZone targetTimeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if (targetTimeZone != null)
            simpleDateFormat.setTimeZone(targetTimeZone);
        return simpleDateFormat.format(date);
    }

    /**
     * Checks Internet connectivity, returns true is network is available else false
     *
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void showDialog(Context context,
                                  DialogBuilder dialogBuilder) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        if (dialogBuilder.getTitle() != null) {
            dialog.setTitle(dialogBuilder.getTitle());
        }

        if (dialogBuilder.getStrArray() != null) {
            dialog.setItems(dialogBuilder.getStrArray(), dialogBuilder.getOnItemSelectListener());

            if (dialogBuilder.getNegativeButtonText() != null) {
                dialog.setNegativeButton(dialogBuilder.getNegativeButtonText(), dialogBuilder.getOnNegativeClick());
            }
        } else {
            if (dialogBuilder.getMessage() != null) {
                dialog.setMessage(dialogBuilder.getMessage());
            }

            if (dialogBuilder.getPositiveButtonText() != null) {
                dialog.setPositiveButton(dialogBuilder.getPositiveButtonText(), dialogBuilder.getOnPositiveCLick());
            }

            if (dialogBuilder.getNegativeButtonText() != null) {
                dialog.setNegativeButton(dialogBuilder.getNegativeButtonText(), dialogBuilder.getOnNegativeClick());
            }

            if (dialogBuilder.getNeutralButtonText() != null) {
                dialog.setNeutralButton(dialogBuilder.getNeutralButtonText(), dialogBuilder.getOnNeutralClick());
            }

        }


        dialog.setCancelable(dialogBuilder.isCancellable());

        if (dialogBuilder.getIcon() > 0) {
            dialog.setIcon(dialogBuilder.getIcon());
        }
        dialog.show();
    }


    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String encodeImage(Context context, Uri path) {
        try {
            final InputStream imageStream = context.getContentResolver().openInputStream(path);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = "data:image/jpeg;base64,"+Util.encodeImage(selectedImage);
            return encodedImage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }


    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}
