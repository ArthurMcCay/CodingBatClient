package com.codebzrk.codingbatclient.data.parsers

import com.codebzrk.codingbatclient.data.models.Challenge
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ChallengeParser(document: Document) {

    private val mDocument = document

    private val go = "Go"
    private val classIndent = "indent"
    private val tBody = "tbody"
    private val tagTd = "td"
    private val tagTr = "tr"
    private val max2 = "max2"
    private val tClass = "class"
    private val h2 = "h2"
    private val span = "span"
    private val aceDiv = "ace_div"

    fun getChallenge() = Challenge("",
                "",false,parseChallengeDescription(), parseExamples(),"","",parseCode())

    private fun parseChallengeDescription() = mDocument.getElementsByClass(max2).first().text()

    private fun parseExamples(): String {
        val leftTable = mDocument
                .getElementsByClass(classIndent)
                .tagName(tBody)
                .tagName(tagTr)
                .tagName(tagTd)
                .first()
        val spans: Elements = leftTable.getElementsByTag(span)
        val title = spans.attr(tClass, h2)[1].text()
        val leftTable1 = leftTable.text().replace(parseChallengeDescription(), "")
        val leftTableArray = leftTable1.split(title)
        val thirdExample: List<String>
        if (leftTableArray.size < 5) {
            thirdExample = leftTableArray[2].split(go)
            return title + thirdExample[0]
        } else {
            thirdExample = leftTableArray[4].split(go)
            return title + leftTableArray[2] + "\n" +
                    title + leftTableArray[3] + "\n" +
                    title + thirdExample[0]
        }
    }

    private fun parseCode() = mDocument.getElementById(aceDiv).text()

}