/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.vinay.library;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vinay.library.data.source.AuthRepository;
import com.vinay.library.data.source.local.AuthLocalDataSource;
import com.vinay.library.data.source.remote.AuthRemoteDataSource;
import com.vinay.library.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link } at compile time.
 */
public class Injection {

    public static AuthRepository provideAuthRepository(@NonNull Context context) {
        checkNotNull(context);
        return AuthRepository.getInstance(AuthRemoteDataSource.getInstance(),
                AuthLocalDataSource.getInstance(new AppExecutors()));
    }

}
