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

package com.codebzrk.codingbatclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.codebzrk.codingbatclient.R
import com.codebzrk.codingbatclient.ui.categories.CategoriesActivity
import kotlinx.android.synthetic.main.login_screen.*

class LoginActivity : AppCompatActivity(), LoginView {

    private var mPresenter: LoginPresenterImpl? = null
    private var isLoginDataPresentFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window: Window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        mPresenter = LoginPresenterImpl(this, LoginInteractorImpl(this))
        mPresenter?.start()

        if (isLoginDataPresentFlag) setContentView(R.layout.splash_screen)
        else {
            setContentView(R.layout.login_screen)
            initialize()
        }
    }

    private fun initialize() {
        login_button.setOnClickListener({
            mPresenter?.onLoginClicked(email_filed.text.toString(),
                    pw_field.text.toString())
        })
    }

    override fun setLoginDataPresentFlag(isLoginDataPresent: Boolean) {
        isLoginDataPresentFlag = isLoginDataPresent
    }

    override fun setEmailError() {
        email_filed.error = resources.getString(R.string.error_email_incorrect)
    }

    override fun setEmailEmpty() {
        email_filed.error = resources.getString(R.string.error_email_empty)
    }

    override fun setPasswordError() {
        pw_field.error = resources.getString(R.string.error_pw_incorrect)
    }

    override fun setPasswordEmpty() {
        pw_field.error = resources.getString(R.string.error_pw_empty)
    }

    override fun showProgress() {
        loading_indicator.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        loading_indicator.visibility = View.INVISIBLE
    }

    override fun openCategories() {
        val intent = Intent(this, CategoriesActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openSignUpScreen() {
        Toast.makeText(this, "Opening sign up screen...", Toast.LENGTH_SHORT).show()
    }

    override fun openResetPasswordScreen() {
        Toast.makeText(this, "Opening reset password screen...", Toast.LENGTH_SHORT).show()
    }

    override fun setInputDisabled() {
        email_filed.isEnabled = false
        pw_field.isEnabled = false
        login_button.isEnabled = false
        forgot_pw.isEnabled = false
        signUpTextView.isEnabled = false
    }

    override fun setInputEnabled() {
        email_filed.isEnabled = true
        pw_field.isEnabled = true
        login_button.isEnabled = true
        forgot_pw.isEnabled = true
        signUpTextView.isEnabled = true
    }
}