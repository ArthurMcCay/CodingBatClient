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

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.codebzrk.codingbatclient.R
import com.codebzrk.codingbatclient.data.models.Challenge
import com.codebzrk.codingbatclient.ui.adapters.ChallengeRVAdapter
import com.codebzrk.codingbatclient.ui.adapters.RecyclerViewClickListener
import com.codebzrk.codingbatclient.ui.editor.EditorActivity
import kotlinx.android.synthetic.main.category_list_activity.*
import java.util.ArrayList

class ChallengesActivity: AppCompatActivity(), ChallengesView, RecyclerViewClickListener {

    private var mPresenter: ChallengesPresenterImpl? = null
    private var mLink = ""
    private var toolbarTitle = ""
    private var dataList = ArrayList<Challenge>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chalenge_list_activity)

        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            mLink = bundle.get("link") as String
            toolbarTitle = bundle.get("title") as String
            toolbar_title.text = toolbarTitle
        }

        initialize()
    }

    private fun initialize() {
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        mPresenter = ChallengesPresenterImpl(this, ChallengesInteractorImpl(this))
        mPresenter?.start(mLink)
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
        val intent = Intent(this, EditorActivity::class.java)
        intent.putExtra("link",dataList[position].link)
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

    override fun setItems(itemList: ArrayList<Challenge>) {
        dataList = itemList
        val adapter = ChallengeRVAdapter(itemList, this, this)
        rv.adapter = adapter
    }
}