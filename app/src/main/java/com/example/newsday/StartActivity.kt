package com.example.newsday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.newsday.login.LoginActivity
import com.example.newsday.login.SpHelp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        lifecycleScope.launch {

            delay(500)
            if (SpHelp.queryIsLoggedIn()) {
                MainActivity.startFromActivity(this@StartActivity)
            } else {
                LoginActivity.startFromActivity(this@StartActivity)
            }
            delay(500)
            //完成
            finish()

        }

    }
}