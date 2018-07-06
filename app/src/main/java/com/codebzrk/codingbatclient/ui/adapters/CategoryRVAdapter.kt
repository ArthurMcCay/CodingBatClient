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

package com.codebzrk.codingbatclient.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codebzrk.codingbatclient.R
import com.codebzrk.codingbatclient.data.models.Category
import kotlinx.android.synthetic.main.category_layout.view.*
import kotlinx.android.synthetic.main.category_title_layout.view.*
import java.util.*
import java.util.regex.Pattern

class CategoryRVAdapter(private val categories: ArrayList<Category>,
                        private val context: Context,
                        listener: RecyclerViewClickListener?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mListener = listener

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):
            RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> CategoryViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.category_layout, parent, false), mListener)
            1 -> CategoryTitleViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.category_title_layout, parent, false))
            else -> CategoryDividerViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.category_divider, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when {
            holder?.itemViewType == 0 -> {
                val categoryViewHolder = holder as CategoryViewHolder
                categoryViewHolder.bindItems(categories[position])
            }
            holder?.itemViewType == 1 -> {
                val categoryTitleViewHolder = holder as CategoryTitleViewHolder
                categoryTitleViewHolder.bindItems(categories[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (categories[position].type) {
            "category" -> 0
            "title" -> 1
            else -> {
                3
            }
        }
    }

    class CategoryViewHolder(itemView: View, listener: RecyclerViewClickListener?):
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var mListener: RecyclerViewClickListener? = null

        init {
            mListener = listener
            itemView.setOnClickListener(this)
        }

        fun bindItems(category: Category) {
            itemView.category_name.text = category.name
            itemView.category_description.text = category.description
            itemView.category_icon.setBackgroundResource(getCategoryDrawable(category.name))
            itemView.rating_bar.numStars = category.progress!![0]
            itemView.rating_bar.rating = category.progress[1].toFloat()
            setUpRatingBar(itemView.rating_bar.progressDrawable as LayerDrawable)
        }

        override fun onClick(view: View?) {
            mListener?.onClick(view!!, adapterPosition)
        }

        private fun getCategoryDrawable(categoryName: String): Int {

            val titleStringLowerCase = categoryName.toLowerCase()

            val patternWarmUp = "warm"
            val patternString = "string"
            val patternArray = "array"
            val patternRecursion = "recursion"
            val patternFunctional = "functional"
            val patternAP = "ap"
            val patternLogic = "logic"
            val patternMap = "map"

            val regexWarmUp = Pattern.compile(patternWarmUp)
            val regexString = Pattern.compile(patternString)
            val regexArray = Pattern.compile(patternArray)
            val regexRecursion = Pattern.compile(patternRecursion)
            val regexFunctional = Pattern.compile(patternFunctional)
            val regexAP = Pattern.compile(patternAP)
            val regexLogic = Pattern.compile(patternLogic)
            val regexMap = Pattern.compile(patternMap)

            val matcherWarmUp = regexWarmUp.matcher(titleStringLowerCase)
            val matcherString = regexString.matcher(titleStringLowerCase)
            val matcherArray = regexArray.matcher(titleStringLowerCase)
            val matcherRecursion = regexRecursion.matcher(titleStringLowerCase)
            val matcherFunctional = regexFunctional.matcher(titleStringLowerCase)
            val matcherAP = regexAP.matcher(titleStringLowerCase)
            val matcherLogic = regexLogic.matcher(titleStringLowerCase)
            val matcherMap = regexMap.matcher(titleStringLowerCase)

            if (matcherWarmUp.find()) return R.drawable.warmup
            if (matcherString.find()) return R.drawable.string
            if (matcherArray.find()) return R.drawable.array
            if (matcherRecursion.find()) return R.drawable.recursion
            if (matcherFunctional.find()) return R.drawable.lambda
            if (matcherMap.find()) return R.drawable.map
            if (matcherAP.find()) return R.drawable.ap
            if (matcherLogic.find()) return R.drawable.logic

            return R.drawable.warmup
        }

        private fun setUpRatingBar(layers: LayerDrawable) {
            DrawableCompat.setTint(layers.getDrawable(0), Color.parseColor("#bebebe"))
            DrawableCompat.setTint(layers.getDrawable(1), Color.parseColor("#ffec23"))
            DrawableCompat.setTint(layers.getDrawable(2), Color.parseColor("#ffec23"))
        }
    }

    class CategoryTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(category: Category) {
            itemView.category_title.text = category.name
        }
    }

    class CategoryDividerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}


