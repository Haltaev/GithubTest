package appsdealer.githubtest.ui.adapter

import android.view.View
import androidx.databinding.BindingAdapter

object CustomBindingAdapter {

    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}