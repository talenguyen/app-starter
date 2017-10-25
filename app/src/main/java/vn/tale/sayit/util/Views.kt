package vn.tale.sayit.util

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import vn.tale.sayit.glide.GlideApp
import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("#,###,###,###")

fun onClick(v: View, onClick: (View) -> Unit) {
  v.setOnClickListener(onClick)
}


fun TextView.setHtml(htmlContent: String) {
  this.text = htmlContent.toHtml()
}

fun TextView.setPrice(price: Float) {
  this.text = formatPrice(price)
}

fun ImageView.setImageUrl(imageUrl: String) {
  GlideApp
      .with(this)
      .load(imageUrl)
      .into(this)
}

fun View.gone() {
  visibility = View.GONE
}

fun View.visible() {
  visibility = View.VISIBLE
}

@SuppressLint("ResourceType")
fun ViewGroup.inflate(@IdRes id: Int): View {
  return LayoutInflater.from(context)
      .inflate(id, this, false)
}

private fun formatPrice(price: Float): CharSequence {
  val result = decimalFormat.format(price.toDouble())
  return "\$$result"
}