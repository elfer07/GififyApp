package ar.com.gififyapp.application

import android.content.Context
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

/**
 * Created by Fernando Moreno on 28/9/2021.
 */
inline fun SearchView.onQueryTextChanged(crossinline onQueryTextChanged: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            onQueryTextChanged(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}

inline fun <T : View> T.hideIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        hide()
    } else {
        show()
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.success(this, message, duration, true).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}