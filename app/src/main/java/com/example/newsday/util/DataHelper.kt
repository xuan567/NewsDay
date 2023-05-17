package com.example.newsday.util

import androidx.lifecycle.MutableLiveData
import com.example.newsday.R
import com.example.newsday.login.CountChange
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/*
匹配是否是手机号
 */
fun isValidPhoneNumber(phoneNumber: String?): Boolean {
    return if (!phoneNumber.isNullOrEmpty()) {
        Pattern.matches("^1[3-9]\\d{9}$", phoneNumber)
    } else false
}
/*
 * 匹配是否是手机验证码【6位数字】
 */
fun isValidPassword(password : String?) : Boolean{
    return if(!password.isNullOrEmpty()){
        Pattern.matches("\\d{6}",password)
    }else false
}

/*
 * 验证码：一分钟倒计时
 * 使用Rxjava实现
 */
fun oneMinuteCountdown(loginCountNumber : MutableLiveData<CountChange?>) {
    val count = 60
    Observable.interval(0,1, TimeUnit.SECONDS)
        .take(60)
        .map {
            CountChange("${count-it}秒后从新发送", R.color.authCodeAfter,false)
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<CountChange> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: CountChange) {
                loginCountNumber.value = t
            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onComplete() {
                val countChange = CountChange("重新获取验证码", R.color.authCode,true)
                loginCountNumber.value = countChange
            }
        })
}