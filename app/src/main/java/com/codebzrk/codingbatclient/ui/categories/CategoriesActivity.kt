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

package com.codebzrk.codingbatclient.ui.categories

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.codebzrk.codingbatclient.R
import com.codebzrk.codingbatclient.data.models.Category
import com.codebzrk.codingbatclient.ui.adapters.CategoryRVAdapter
import com.codebzrk.codingbatclient.ui.adapters.RecyclerViewClickListener
import com.codebzrk.codingbatclient.ui.challenges.ChallengesActivity
import kotlinx.android.synthetic.main.category_list_activity.*
import kotlin.collections.ArrayList

class CategoriesActivity : AppCompatActivity(), CategoriesView, RecyclerViewClickListener {

    private var mPresenter: CategoriesPresenterImpl? = null
    private var dataList: ArrayList<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_list_activity)
        initialize()
    }

    private fun initialize() {
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        mPresenter = CategoriesPresenterImpl(this, GetCategoriesInteractorImpl(this))
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    override fun onClick(view: View, position: Int) {
        val intent = Intent(this, ChallengesActivity::class.java)
        intent.putExtra("title",dataList?.get(position)?.name)
        intent.putExtra("desc",dataList?.get(position)?.description)
        intent.putExtra("link",dataList?.get(position)?.link)
        startActivity(intent)
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
        rv.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
        rv.visibility = View.VISIBLE
    }

    override fun setItems(itemList: ArrayList<Category>) {
        val adapter = CategoryRVAdapter(itemList, this, this)
        dataList = itemList
        rv.adapter = adapter
    }
}