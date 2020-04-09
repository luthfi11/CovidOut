package com.luthfi.covidout.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luthfi.covidout.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch {
            delay(2000)
            finish()
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}
