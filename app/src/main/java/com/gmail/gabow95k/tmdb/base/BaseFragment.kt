package com.gmail.gabow95k.tmdb.base

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.custom.CustomLoader

open class BaseFragment<T, B : ViewBinding> : Fragment(), BaseView {

    protected var presenter: T? = null
    protected var binding: B? = null

    private var loader: CustomLoader? = null

    override fun showLoader() {
        getLoader()!!.show(childFragmentManager, CustomLoader::class.java.name)
    }

    override fun hideLoader() {
        getLoader()!!.dismiss()
    }

    override fun showDialog(message: String?) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
            builder.apply {
                setPositiveButton(
                    R.string.accept
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.create()
        }
        alertDialog!!.show()
    }

    override fun showDialog(resourceId: Int) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(resourceId))
            builder.apply {
                setPositiveButton(
                    R.string.accept
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.create()
        }
        alertDialog!!.show()

    }

    private fun getLoader(): CustomLoader? {
        return if (loader != null) loader else CustomLoader().also { loader = it }
    }

}