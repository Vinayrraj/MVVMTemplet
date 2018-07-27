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

package com.vinay.library.data.source;

import android.support.annotation.NonNull;

import com.vinay.library.R;
import com.vinay.library.app.LibraryApplication;
import com.vinay.library.data.user.User;
import com.vinay.library.util.ActivityUtils;

import static com.google.common.base.Preconditions.checkNotNull;


public class AuthRepository implements AuthDataSource {

    private volatile static AuthRepository _INSTANCE = null;

    private final AuthDataSource mAuthRemoteDataSource;

    private final AuthDataSource mAuthLocalDataSource;


    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private AuthRepository(@NonNull AuthDataSource tasksRemoteDataSource,
                           @NonNull AuthDataSource tasksLocalDataSource) {
        mAuthRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        mAuthLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param authRemoteDataSource the backend data source
     * @param authLocalDataSource  the device storage data source
     * @return the {@link AuthRepository} instance
     */
    public static AuthRepository getInstance(AuthDataSource authRemoteDataSource,
                                             AuthDataSource authLocalDataSource) {
        if (_INSTANCE == null) {
            synchronized (AuthRepository.class) {
                if (_INSTANCE == null) {
                    _INSTANCE = new AuthRepository(authRemoteDataSource, authLocalDataSource);
                }
            }
        }
        return _INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(AuthDataSource, AuthDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        _INSTANCE = null;
    }

    @Override
    public void login(@NonNull final AuthenticationCallback callback) {

        if (ActivityUtils.isNetworkAvailable(LibraryApplication.getContext())) {
            checkNotNull(callback);

            AuthenticationCallback tempCallBack = new AuthenticationCallback() {
                @Override
                public void onSuccess(User user) {
                    updateUser(user);
                    callback.onSuccess(user);
                }

                @Override
                public void onFailure(String message) {
                    callback.onFailure(message);
                }

                @Override
                public String getEmail() {
                    return callback.getEmail();
                }

                @Override
                public String getPassword() {
                    return callback.getPassword();
                }

                @Override
                public String getScope() {
                    return callback.getScope();
                }
            };
            mAuthRemoteDataSource.login(tempCallBack);
        } else {
            callback.onFailure(LibraryApplication.getContext().getString(R.string.label_no_connectivity));
        }
    }

    @Override
    public void updateUser(User user) {
        mAuthLocalDataSource.updateUser(user);
    }

    @Override
    public void getUser(UserDataCallback callBack) {
        mAuthLocalDataSource.getUser(callBack);
    }


    @Override
    public void logout(UserDataCallback callBack) {
        mAuthLocalDataSource.logout(callBack);
    }

}
