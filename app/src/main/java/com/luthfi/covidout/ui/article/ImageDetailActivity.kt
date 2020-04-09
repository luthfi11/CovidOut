package com.luthfi.covidout.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.luthfi.covidout.R
import kotlinx.android.synthetic.main.activity_image_detail.*

class ImageDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        setUpToolbar()

        val image = intent.getStringExtra("image")
        Glide.with(this).load(image).into(imgPhoto)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbarPhoto)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
