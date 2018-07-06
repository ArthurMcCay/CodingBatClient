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

package com.codebzrk.codingbatclient.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet

class CodeEditor(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private var rect: Rect? = null
    private var paint: Paint? = null

    init {
        rect = Rect()
        paint = Paint()
        paint?.style = Paint.Style.FILL
        paint?.color = Color.BLACK
        paint?.textSize = 20f
    }

    override fun onDraw(canvas: Canvas?) {
        var baseLine = baseline.toFloat()
        val rectLeft = rect!!.left.toFloat()
        for (i in 0..lineCount) {
            canvas?.drawText("  ${i+1}", rectLeft, baseLine, paint)
            baseLine += lineHeight
        }
        super.onDraw(canvas)
    }

}