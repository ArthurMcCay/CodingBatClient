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

package com.codebzrk.codingbatclient.data.web

import android.util.Log
import com.codebzrk.codingbatclient.ui.categories.GetCategoriesInteractor
import com.codebzrk.codingbatclient.ui.challenges.ChallengesInteractor
import com.codebzrk.codingbatclient.ui.editor.EditorInteractor
import com.codebzrk.codingbatclient.ui.login.LoginInteractor
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * @author Arthur Alan McCay <arthur.alan.mccay@gmail.com
 * @version 1.0.0
 * @since 6/05/2018
 */

class WebService {

    private val sessionIdTag: String = "JSESSIONID"
    private val baseURL: String = "http://codingbat.com/"
    private val userAgent: String = "Mozilla"

    fun getAuthResult(authenticationListener: LoginInteractor.AuthenticationListener,
                      login: String,
                      password: String) {
        var authenticationResult: AuthenticationResult?
        doAsync {
            val response = Jsoup
                    .connect(baseURL+"login")
                    .header("POST","/login HTTP/1.1")
                    .userAgent(userAgent)
                    .method(Connection.Method.POST)
                    .followRedirects(false)
                    .referrer(baseURL)
                    .data("uname", login)
                    .data("pw", password)
                    .execute()
            authenticationResult = AuthenticationResult(response)
        uiThread {
            if (authenticationResult == null) authenticationListener.onError()
            else authenticationListener.onSuccess(authenticationResult!!)
        }
        }
    }

    fun getCategoryList(onCategoryLoadListener: GetCategoriesInteractor.OnCategoryLoadListener,
                        sessionId: String) {
        var document: Document
        val lang = "java"
        doAsync {
            document = Jsoup.connect(baseURL + lang)
                    .cookie(sessionIdTag, sessionId)
                    .method(Connection.Method.GET)
                    .userAgent(userAgent)
                    .referrer(baseURL + lang)
                    .get()
            uiThread {
                onCategoryLoadListener.onSuccess(document)
            }
        }
    }

    fun getChallengesList(onChallengeLoadListener: ChallengesInteractor.OnChallengeLoadListener,
                          sessionId: String, link: String) {
        var document: Document
        val lang = "java"
        doAsync {
            document = Jsoup.connect(baseURL + link)
                    .cookie(sessionIdTag, sessionId)
                    .method(Connection.Method.GET)
                    .userAgent(userAgent)
                    .referrer(baseURL + lang)
                    .get()
            uiThread {
                onChallengeLoadListener.onSuccess(document)
            }
        }
    }

    fun getChallenge(onChallengeLoadListener: EditorInteractor.OnChallengeLoadListener,
                          sessionId: String, uName: String, link: String) {
        var document: Document
        val lang = "java"
        doAsync {
            document = Jsoup.connect(baseURL + link)
                    .cookie(sessionIdTag, sessionId)
                    .method(Connection.Method.GET)
                    .userAgent(userAgent)
                    .referrer(baseURL + lang)
                    .data("cuname",uName)
                    .get()
            uiThread {
                onChallengeLoadListener.onSuccess(document)
            }
        }
    }
}