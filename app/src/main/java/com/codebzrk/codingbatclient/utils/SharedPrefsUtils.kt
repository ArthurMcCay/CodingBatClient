/*
 *
 *  * Copyright (C) 2018 Arthur McCay.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.codebzrk.codingbatclient.utils

import android.content.Context
import android.content.SharedPreferences
import com.codebzrk.codingbatclient.data.models.User
import com.codebzrk.codingbatclient.data.web.AuthenticationResult

class SharedPrefsUtils(context: Context) {

    private val mContext = context
    private val prefsTag = "prefs"
    private val userNameTag = "userName"
    private val userPasswordTag = "password"
    private val sessionIdTag = "sessionId"
    private val cookieKey = "JSESSIONID"

    fun saveAuthData(userName: String, pw: String, authResult: AuthenticationResult) {
        val sharedPreferencesEditor = mContext.getSharedPreferences(prefsTag, 0)?.edit()
        sharedPreferencesEditor?.putString(userNameTag, userName)
        sharedPreferencesEditor?.putString(userPasswordTag, pw)
        sharedPreferencesEditor?.putString(sessionIdTag,
                authResult.response.cookies()[cookieKey])
        sharedPreferencesEditor?.apply()
    }

    fun saveSessionData(authResult: AuthenticationResult) {
        val sharedPreferencesEditor = mContext.getSharedPreferences(prefsTag, 0)?.edit()
        sharedPreferencesEditor?.putString(sessionIdTag,
                authResult.response.cookies()[cookieKey])
        sharedPreferencesEditor?.apply()
    }

    fun getAuthCredentials(): User {
        val sharedPrefs: SharedPreferences =
                mContext.getSharedPreferences(prefsTag, Context.MODE_PRIVATE)
        return User(sharedPrefs.getString(userNameTag, ""),
                sharedPrefs.getString(userPasswordTag, ""))
    }

    fun getSessionId(): String {
        val sharedPrefs: SharedPreferences =
                mContext.getSharedPreferences(prefsTag, Context.MODE_PRIVATE)
        return sharedPrefs.getString(sessionIdTag, "")
    }

    fun checkIfAuthCredentialsPresent(): Boolean {
        val sharedPrefs: SharedPreferences =
                mContext.getSharedPreferences(prefsTag, Context.MODE_PRIVATE)
        return sharedPrefs.contains(userNameTag)
    }

}