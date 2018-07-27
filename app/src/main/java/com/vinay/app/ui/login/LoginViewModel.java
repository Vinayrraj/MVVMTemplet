package com.vinay.app.ui.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.vinay.library.data.source.AuthDataSource;
import com.vinay.library.data.source.AuthRepository;


/**
 * Created by admin on 28-03-2018.
 */

public class LoginViewModel extends AndroidViewModel {

    private AuthRepository mAuthRepository;

    public LoginViewModel(@NonNull Application context, AuthRepository authRepository) {
        super(context);
        mAuthRepository = authRepository;
    }

    public void login(@NonNull final AuthDataSource.AuthenticationCallback callback) {
        mAuthRepository.login(callback);
    }

    public void getUser(AuthDataSource.UserDataCallback callBack) {
        mAuthRepository.getUser(callBack);
    }

}
