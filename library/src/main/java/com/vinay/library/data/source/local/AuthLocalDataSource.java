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

package com.vinay.library.data.source.local;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.vinay.library.app.LibraryApplication;
import com.vinay.library.data.user.User;
import com.vinay.library.data.source.AuthDataSource;
import com.vinay.library.util.AppExecutors;

import static android.content.Context.MODE_PRIVATE;


/**
 * Concrete implementation of a data source as a db.
 */
public class AuthLocalDataSource implements AuthDataSource {

    private static volatile AuthLocalDataSource _INSTANCE;
    private static volatile User mUser = null;

    public interface AUTH_PREFS {
        String AUTH_PREFS_NAME = "AUTH_PREFS_NAME";
        String ID = "id";
        String USER_ID = "userId";
        String PROVIDER_ID = "providerId";
        String PROVIDER_NAME = "providerName";
        String TOKEN = "token";
        String PROVIDER_OFFICIALS = "officials";
    }


    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private AuthLocalDataSource(@NonNull AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
    }

    public static AuthLocalDataSource getInstance(@NonNull AppExecutors appExecutors) {
        if (_INSTANCE == null) {
            synchronized (AuthLocalDataSource.class) {
                if (_INSTANCE == null) {
                    _INSTANCE = new AuthLocalDataSource(appExecutors);

                }
            }
        }
        return _INSTANCE;
    }


    @VisibleForTesting
    static void clearInstance() {
        _INSTANCE = null;
    }

    @Override
    public void login(@NonNull AuthenticationCallback callback) {
        // Leave it blank
    }

    @Override
    public void updateUser(final User user) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            mUser = user;
                            LibraryApplication.getContext().setUser(user);
                        }
                    });
                    SharedPreferences.Editor editor = LibraryApplication.getContext().getSharedPreferences(AUTH_PREFS.AUTH_PREFS_NAME, MODE_PRIVATE).edit();
//                    editor.putInt(AUTH_PREFS.ID, user.getUserId());
//                    editor.putInt(AUTH_PREFS.USER_ID, user.getUserId());
//                    editor.putInt(AUTH_PREFS.PROVIDER_ID, user.getProviderId());
//                    editor.putString(AUTH_PREFS.PROVIDER_NAME, user.getProviderName());
//                    if (user.getOfficials() != null) {
//                        editor.putString(AUTH_PREFS.PROVIDER_OFFICIALS, new Gson().toJson(user.getOfficials()));
//                    } else {
//                        editor.putString(AUTH_PREFS.PROVIDER_OFFICIALS, null);
//                    }
//                    editor.putString(AUTH_PREFS.TOKEN, user.getToken());
                    editor.apply();
                }
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getUser(@NonNull final UserDataCallback callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (mUser != null) {
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null)
                                callBack.onSuccess(mUser);
                        }
                    });

                } else {
                    SharedPreferences prefs = LibraryApplication.getContext().getSharedPreferences(AUTH_PREFS.AUTH_PREFS_NAME, MODE_PRIVATE);
                    int userId = prefs.getInt(AUTH_PREFS.USER_ID, -1);
                    if (userId != -1) {
                        final User user = new User();
//                        user.setUserId(prefs.getInt(AUTH_PREFS.ID, -1));
//                        user.setUserId(userId);
//                        user.setProviderId(prefs.getInt(AUTH_PREFS.PROVIDER_ID, -1));
//                        user.setProviderName(prefs.getString(AUTH_PREFS.PROVIDER_NAME, null));
//                        user.setToken(prefs.getString(AUTH_PREFS.TOKEN, null));
//                        if (prefs.getString(AUTH_PREFS.PROVIDER_OFFICIALS, null) != null) {
//                            user.setOfficials((List<Officials>) new Gson().fromJson(prefs.getString(AUTH_PREFS.PROVIDER_OFFICIALS, null),
//                                    new TypeToken<List<Officials>>() {
//                                    }.getType()));
//                        }
                        mAppExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                mUser = user;
                                LibraryApplication.getContext().setUser(user);
                                if (callBack != null)
                                    callBack.onSuccess(user);
                            }
                        });

                    } else {
                        mAppExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (callBack != null)
                                    callBack.onFailure();
                            }
                        });
                    }
                }

            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void logout(final UserDataCallback callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = LibraryApplication.getContext().getSharedPreferences(AUTH_PREFS.AUTH_PREFS_NAME, MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        mUser = null;
                        LibraryApplication.getContext().setUser(null);
                        callBack.onSuccess(null);
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

}
