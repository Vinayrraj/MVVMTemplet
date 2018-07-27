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

package com.vinay.library.data.source.remote;

import android.support.annotation.NonNull;

import com.vinay.library.R;
import com.vinay.library.app.LibraryApplication;
import com.vinay.library.data.source.AuthDataSource;
import com.vinay.library.data.user.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class AuthRemoteDataSource implements AuthDataSource {

    private static AuthRemoteDataSource _INSTANCE;

    public static AuthRemoteDataSource getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new AuthRemoteDataSource();
        }
        return _INSTANCE;
    }

    // Prevent direct instantiation.
    private AuthRemoteDataSource() {
    }


    @Override
    public void login(@NonNull final AuthenticationCallback callback) {

        final Map<String, Object> params = new HashMap<>();
        params.put(APIExtras.Key.USERNAME, callback.getEmail());
        params.put(APIExtras.Key.PASSWORD, callback.getPassword());
        params.put(APIExtras.Key.SCOPE, callback.getScope());
        params.put(APIExtras.Key.GRANT_TYPE, APIExtras.Values.GRANT_TYPE_PASSWORD);
        params.put(APIExtras.Key.CLIENT_ID, LibraryApplication.getContext().getString(R.string.param_client_id));
        params.put(APIExtras.Key.CLIENT_SECRET, APIExtras.Values.BLANK);

        callLoginApi(callback, params);
    }

    @Override
    public void refreshToken(@NonNull AuthenticationCallback callback) {
        final Map<String, Object> params = new HashMap<>();
        params.put(APIExtras.Key.GRANT_TYPE, APIExtras.Values.GRANT_TYPE_REFRESH_TOKEN);
        params.put(APIExtras.Key.REFRESH_TOKEN, LibraryApplication.getContext().getUser().getRefreshToken());
        params.put(APIExtras.Key.CLIENT_ID, LibraryApplication.getContext().getString(R.string.param_client_id));

        callLoginApi(callback, params);
    }

    private void callLoginApi(@NonNull final AuthenticationCallback callback, Map<String, Object> params) {
        Call<User> apiCall = APIBuilder.getBuilder().getService().login(
                LibraryApplication.getContext().getString(R.string.api_login),
                APIExtras.Values.APPLICATION_JSON,
                APIExtras.Values.APPLICATION_JSON,
                params);
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                callback.onSuccess(user);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void updateUser(User user) {
        // Leave it blank
    }

    @Override
    public void getUser(UserDataCallback callBack) {
        // Leave it blank
    }


    @Override
    public void logout(UserDataCallback callBack) {
        // Leave it blank
    }
}
