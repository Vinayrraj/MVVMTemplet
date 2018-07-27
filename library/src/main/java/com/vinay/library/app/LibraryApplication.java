package com.vinay.library.app;

import android.app.Application;

import com.vinay.library.Injection;
import com.vinay.library.data.user.User;
import com.vinay.library.data.source.AuthRepository;

/**
 * Created by admin on 28-03-2018.
 */

public class LibraryApplication extends Application {

    private static LibraryApplication context;
    private User user;
    private AuthRepository authRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if (authRepository == null) {
            authRepository = Injection.provideAuthRepository(getApplicationContext());
        }
        authRepository.getUser(null);
    }

    public static LibraryApplication getContext() {
        return context;
    }

    public User getUser() {
        return user;
    }

    public synchronized void setUser(User user) {
        this.user = user;
    }
}
