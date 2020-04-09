package com.luthfi.covidout.ui.article

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.luthfi.covidout.R


class ImageAdapter(private val context: Context, private val imageList: List<String>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_image, container, false)
        val imageView: ImageView = view.findViewById(R.id.imgArticle)

        Glide.with(context).load(getImage(position)).into(imageView)
        imageView.setOnClickListener {
            val intent = Intent(context, ImageDetailActivity::class.java).apply {
                putExtra("image", getImage(position))
            }
            context.startActivity(intent)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` == view
    }

    override fun getCount(): Int {
        return imageList.size
    }

    private fun getImage(position: Int): String {
        return imageList[position]
    }
}