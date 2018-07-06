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

import com.codebzrk.codingbatclient.data.models.Category
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * @author Arthur Alan McCay <arthur.alan.mccay@gmail.com
 * @version 1.0.0
 * @since 6/05/2018
 */

class CategoryParser(private val document: Document) {

    private val classSumm: String = "summ"
    private val tagHref: String = "href"
    private val tagA: String = "a"
    private val tagImg: String = "img"
    private val tagSrc: String = "src"

    enum class Difficulty {
        BASIC,
        MEDIUM,
        HARD
    }

    fun parseCategoryList(): ArrayList<Category> {

        val dataList = ArrayList<Category>()

        val categoryBasic = ArrayList<Category>()
        val categoryMedium = ArrayList<Category>()
        val categoryHard = ArrayList<Category>()

        val parsedCategoryElements : Elements = document.getElementsByClass(classSumm)

        for (element: Element in parsedCategoryElements) {

            val categoryName: String = element.getElementsByTag(tagA).text()

            val categoryDescription: String = element.text()
                    .replace("...","")
                    .replace("$categoryName ","")

            val categoryLink: String = element
                    .getElementsByTag(tagA).attr(tagHref).substring(1)

            val rating: IntArray = getProgressStars(element)

            val category = Category(categoryName,
                    categoryDescription, categoryLink, rating, "category")

            val categoryDifficulty = getCategoryDifficulty(category)

            if (categoryDifficulty == Difficulty.BASIC) categoryBasic.add(category)
            if (categoryDifficulty == Difficulty.MEDIUM) categoryMedium.add(category)
            if (categoryDifficulty == Difficulty.HARD) categoryHard.add(category)

        }

        categoryBasic.sortBy { category -> category.name }
        categoryMedium.sortBy { category -> category.name }
        categoryHard.sortBy { category -> category.name }

        dataList.add(Category("SIMPLE PROBLEMS",
                "", "", null, "title"))
        dataList += categoryBasic
        dataList.add(Category("",
                "", "", null, "div"))
        dataList.add(Category("MEDIUM PROBLEMS",
                "", "", null, "title"))
        dataList += categoryMedium
        dataList.add(Category("",
                "", "", null, "div"))
        dataList.add(Category("HARD PROBLEMS",
                "", "", null, "title"))
        dataList += categoryHard
        dataList.add(Category("",
                "", "", null, "div"))

        return dataList
    }

    private fun getCategoryDifficulty(category: Category): Difficulty {

        val descriptionStringLowerCase = category.description.toLowerCase()
        val titleStringLowerCase = category.name.toLowerCase()

        val patternBasic = "basic"
        val patternSimple = "simple"
        val patternMedium = "medium"
        val patternHard = "harder"
        val patternBasic1 = "1"
        val patternMedium2 = "2"
        val patternHard3 = "3"

        val regexBasic = Pattern.compile(patternBasic)
        val regexSimple = Pattern.compile(patternSimple)
        val regexMedium = Pattern.compile(patternMedium)
        val regexHard = Pattern.compile(patternHard)
        val regexBasic1 = Pattern.compile(patternBasic1)
        val regexMedium2 = Pattern.compile(patternMedium2)
        val regexHard3 = Pattern.compile(patternHard3)

        val matcherBasic = regexBasic.matcher(descriptionStringLowerCase)
        val matcherSimple = regexSimple.matcher(descriptionStringLowerCase)
        val matcherMedium = regexMedium.matcher(descriptionStringLowerCase)
        val matcherHard = regexHard.matcher(descriptionStringLowerCase)
        val matcherBasic1 = regexBasic1.matcher(titleStringLowerCase)
        val matcherMedium2 = regexMedium2.matcher(titleStringLowerCase)
        val matcherHard3 = regexHard3.matcher(titleStringLowerCase)

        if (matcherBasic.find()) return Difficulty.BASIC
        if (matcherSimple.find()) return Difficulty.BASIC
        if (matcherMedium.find()) return Difficulty.MEDIUM
        if (matcherHard.find()) return Difficulty.HARD
        if (matcherBasic1.find()) return Difficulty.BASIC
        if (matcherMedium2.find()) return Difficulty.MEDIUM
        if (matcherHard3.find()) return Difficulty.HARD

        return Difficulty.MEDIUM
    }

    private fun getProgressStars(categoryElement: Element): IntArray {
        val results = IntArray(2)
        val allImages: Elements = categoryElement.getElementsByTag(tagImg)
        var numStars = 0
        var numStarsCompleted = 0
        for (e: Element in allImages) {
            if (e.attr(tagSrc) == "/s1.jpg") {
                numStars++
                continue
            }
            if (e.attr(tagSrc) == "/s2.jpg") {
                numStars++
                numStarsCompleted++
                continue
            }
        }
        results[0] = numStars
        results[1] = numStarsCompleted
        return results
    }

}