package com.example.newsday.login

import com.example.newsday.R

data class CountChange(var textCountNumber : String? = null,
                       var textColor : Int = (R.color.authCodeAfter),
                       var isEnable : Boolean = false )

data class LoginAutoCode(
    var error : String? = null,
    var success : Int? = null,
)

data class LoginResult(
    val success: Boolean = false,
    val error: String? = null
)

// LiveData中用来判断登录状态的bean
data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isUserNameValid: Boolean = false,
    val isPasswordValid : Boolean = false
)