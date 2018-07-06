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

package com.codebzrk.codingbatclient.data.parsers

import com.codebzrk.codingbatclient.data.models.Challenge
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class ChallengeListParser(private val document: Document) {

    private val tagImage = "img"
    private val classIndent = "indent"
    private val tagTd = "td"
    private val tagSrc = "src"
    private val tagHref = "href"
    private val tagA = "a"
    private val imgName = "/c2.jpg"

    fun parseChallengeList(): ArrayList<Challenge> {
        val challengeList = ArrayList<Challenge>()
        val challengeElements: Elements = document.getElementsByClass(classIndent).select(tagTd)
        for (element: Element in challengeElements) {
            val isCompleted = element.getElementsByTag(tagImage).attr(tagSrc) == imgName
            val name = element.getElementsByTag(tagA).text()
            val link = element.getElementsByTag(tagA).attr(tagHref).substring(1)
            challengeList.add(Challenge(name, link, isCompleted, "",
                    "","","",""))
        }
        return challengeList
    }
}