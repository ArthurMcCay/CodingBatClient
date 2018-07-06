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

package com.codebzrk.codingbatclient.ui.editor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.codebzrk.codingbatclient.R
import com.codebzrk.codingbatclient.data.models.Challenge
import kotlinx.android.synthetic.main.editor_layout.*

class EditorActivity: AppCompatActivity(), EditorView {

    private var mPresenter: EditorPresenterImpl? = null
    private var isTaskShown = true
    private var mLink = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_layout)

        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            mLink = bundle.get("link") as String
        }

        initialize()
    }

    private fun initialize() {
        taskVisibilityIcon.setOnClickListener { toggleShowTask() }
        mPresenter = EditorPresenterImpl(this, EditorInteractorImpl(this))
        mPresenter?.start(mLink)
    }

    private fun toggleShowTask() {
        if (isTaskShown) {
            task.visibility = View.GONE
            examples.visibility = View.GONE
            isTaskShown = false
        } else {
            task.visibility = View.VISIBLE
            examples.visibility = View.VISIBLE
            isTaskShown = true
        }
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun setUpView(challenge: Challenge) {
        task.text = challenge.description
        examples.text = challenge.examples
        editor_text_edit.setText(challenge.code)
    }
}