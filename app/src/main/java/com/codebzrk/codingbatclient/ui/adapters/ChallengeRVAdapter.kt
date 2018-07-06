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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codebzrk.codingbatclient.R
import com.codebzrk.codingbatclient.data.models.Challenge
import com.codebzrk.codingbatclient.ui.challenges.ChallengesView
import kotlinx.android.synthetic.main.challenge_layout.view.*
import java.util.ArrayList

class ChallengeRVAdapter(private val challenges: ArrayList<Challenge>,
                         private val context: Context,
                         listener: RecyclerViewClickListener?) :
        RecyclerView.Adapter<ChallengeRVAdapter.ChallengeViewHolder>() {

    private val mListener = listener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):
            ChallengeViewHolder {
        return ChallengeViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.challenge_layout, parent, false), mListener)
    }

    override fun getItemCount(): Int {
        return challenges.size
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder?, position: Int) {
        holder?.bindItems(challenges[position])
    }

    class ChallengeViewHolder(itemView: View, listener: RecyclerViewClickListener?)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var mListener: RecyclerViewClickListener? = null

        init {
            mListener = listener
            itemView.setOnClickListener(this)
        }

        fun bindItems(challenge: Challenge) {
            itemView.challenge_name.text = challenge.name
            if (challenge.completed) itemView.is_solved_img.setImageResource(R.drawable.completed)
            else itemView.is_solved_img.setImageResource(R.drawable.uncompleted)
        }

        override fun onClick(view: View?) {
            mListener?.onClick(view!!, adapterPosition)
        }

    }
}