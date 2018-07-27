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

package com.vinay.library.data;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.vinay.library.R;
import com.vinay.library.app.LibraryApplication;
import com.vinay.library.data.source.AuthDataSource;
import com.vinay.library.data.user.User;
import com.vinay.library.util.ActivityUtils;
import com.vinay.library.util.AppExecutors;


/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeRemoteDataSource implements AuthDataSource {

    private static FakeRemoteDataSource INSTANCE;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private FakeRemoteDataSource(@NonNull AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
    }


    public static FakeRemoteDataSource getInstance(@NonNull AppExecutors appExecutors) {
        if (INSTANCE == null) {
            INSTANCE = new FakeRemoteDataSource(appExecutors);
        }
        return INSTANCE;
    }


    @Override
    public void login(@NonNull final AuthenticationCallback callback) {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                final User user = new Gson().fromJson(ActivityUtils.getStringFromRaw(LibraryApplication.getContext(), R.raw.json_login), User.class);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(user);
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void refreshToken(@NonNull AuthenticationCallback callback) {

    }

    @Override
    public void updateUser(@NonNull User user) {
        // Leave it blank
    }

    @Override
    public void getUser(@NonNull UserDataCallback callBack) {

    }


    @Override
    public void logout(UserDataCallback callBack) {
// Leave it blank
    }


}
