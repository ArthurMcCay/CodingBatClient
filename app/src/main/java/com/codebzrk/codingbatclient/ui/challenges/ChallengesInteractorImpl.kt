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

package com.codebzrk.codingbatclient.ui.challenges

import android.content.Context
import com.codebzrk.codingbatclient.data.parsers.ChallengeListParser
import com.codebzrk.codingbatclient.data.web.WebService
import com.codebzrk.codingbatclient.utils.SharedPrefsUtils
import org.jsoup.nodes.Document

class ChallengesInteractorImpl(context: Context) :
        ChallengesInteractor, ChallengesInteractor.OnChallengeLoadListener {

    private val mContext: Context = context
    private val sharedPrefsUtils = SharedPrefsUtils(mContext)
    private var onFinishedListener: ChallengesInteractor.OnFinishedListener? = null

    override fun getChallengeList(listener: ChallengesInteractor.OnFinishedListener,
                                  link: String) {
        onFinishedListener = listener
        val webService = WebService()
        webService.getChallengesList(this,
                sharedPrefsUtils.getSessionId(),
                link)
    }

    override fun onSuccess(document: Document) {
        val challengeParser = ChallengeListParser(document)
        onFinishedListener?.onFinished(challengeParser.parseChallengeList())
    }

    override fun onError() {
        // TODO: show some error
    }
}