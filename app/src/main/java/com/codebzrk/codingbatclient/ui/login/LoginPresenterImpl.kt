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

class LoginPresenterImpl(loginView: LoginView,
                         loginInteractor: LoginInteractor) :
        LoginPresenter, LoginInteractor.OnFinishedListener {

    private var mLoginView: LoginView? = loginView
    private var mLoginInteractor: LoginInteractor? = loginInteractor

    override fun start() {
        if (mLoginInteractor!!.checkIfLoginDataIsPresent()) {
            mLoginView!!.setLoginDataPresentFlag(true)
            mLoginInteractor?.performLogin(this)
        } else {
            mLoginView!!.setLoginDataPresentFlag(false)
        }
    }

    override fun onLoginClicked(email: String, password: String) {
        mLoginView?.setInputDisabled()
        mLoginView?.showProgress()
        mLoginInteractor?.performLogin(this, email, password)
    }

    override fun onFinished() {
        mLoginView?.openCategories()
    }

    override fun onEmailEmpty() {
        mLoginView?.setInputEnabled()
        mLoginView?.hideProgress()
        mLoginView?.setEmailEmpty()
    }

    override fun onEmailError() {
        mLoginView?.setInputEnabled()
        mLoginView?.hideProgress()
        mLoginView?.setEmailError()
    }

    override fun onPasswordEmpty() {
        mLoginView?.setInputEnabled()
        mLoginView?.hideProgress()
        mLoginView?.setPasswordEmpty()
    }

    override fun onPasswordError() {
        mLoginView?.setInputEnabled()
        mLoginView?.hideProgress()
        mLoginView?.setPasswordError()
    }
}