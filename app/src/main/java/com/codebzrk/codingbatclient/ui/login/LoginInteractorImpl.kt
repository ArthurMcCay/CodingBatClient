package com.codebzrk.codingbatclient.ui.login

import android.content.Context
import android.util.Patterns
import com.codebzrk.codingbatclient.data.models.User
import com.codebzrk.codingbatclient.data.web.AuthenticationResult
import com.codebzrk.codingbatclient.data.web.WebService
import com.codebzrk.codingbatclient.utils.SharedPrefsUtils
import java.util.regex.Pattern

class LoginInteractorImpl(context: Context) :
        LoginInteractor, LoginInteractor.AuthenticationListener  {

    private var mContext: Context = context
    private var mListener: LoginInteractor.OnFinishedListener? = null
    private var mEmail: String? = null
    private var mPassword: String? = null
    private val sharedPrefsUtils = SharedPrefsUtils(mContext)

    override fun performLogin(listener: LoginInteractor.OnFinishedListener) {

        val user: User = sharedPrefsUtils.getAuthCredentials()

        mListener = listener

        val webService = WebService()
        webService.getAuthResult(this, user.email, user.password)
    }

    override fun performLogin(listener: LoginInteractor.OnFinishedListener,
                              email: String,
                              password: String) {

        mEmail = email
        mPassword = password

        mListener = listener

        if (!checkIfEmailNotEmpty(email)) {
            listener.onEmailEmpty()
            return
        }
        if (!checkIfPasswordNotEmpty(password)) {
            listener.onPasswordEmpty()
            return
        }
        if (checkIfEmailPattern(email)) {
            listener.onEmailError()
            return
        }

        val webService = WebService()
        webService.getAuthResult(this, email, password)
    }

    override fun onSuccess(authenticationResult: AuthenticationResult) {
        if (checkIfLoginSuccessful(authenticationResult.response.header("Location"))) {
            if (mEmail != null && mPassword != null) {
                sharedPrefsUtils.saveAuthData(mEmail!!, mPassword!!, authenticationResult)
            } else {
                sharedPrefsUtils.saveSessionData(authenticationResult)
            }
            mListener?.onFinished()
        }
        else {
            mListener?.onPasswordError()
            mListener?.onEmailError()
        }
    }

    override fun onError() {
        mListener?.onPasswordError()
        mListener?.onEmailError()
    }

    override fun checkIfLoginDataIsPresent(): Boolean
            = sharedPrefsUtils.checkIfAuthCredentialsPresent()

    private fun checkIfEmailNotEmpty(email: String) = email.isNotEmpty()

    private fun checkIfPasswordNotEmpty(password: String) = password.isNotEmpty()

    private fun checkIfEmailPattern(email: String) = !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun checkIfLoginSuccessful(cookieLocation: String): Boolean {
        val pattern = "Failed"
        val regex = Pattern.compile(pattern)
        val matcher = regex.matcher(cookieLocation)
        return !matcher.find()
    }
}