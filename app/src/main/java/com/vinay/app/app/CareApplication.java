package com.vinay.app.app;

import com.vinay.library.app.LibraryApplication;


public class CareApplication extends LibraryApplication {

    private static CareApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


}
