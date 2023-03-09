package com.gmail.gabow95k.tmdb.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.custom.CustomLoader
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
        MaterialAlertDialogBuilder(requireContext(), R.style.MyMaterialAlertDialog)
            .setTitle(getString(R.string.alert))
            .setMessage(message)
            .setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun showDialog(resourceId: Int) {
        MaterialAlertDialogBuilder(requireContext(), R.style.MyMaterialAlertDialog)
            .setTitle(getString(R.string.alert))
            .setMessage(getString(resourceId))
            .setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

    private fun getLoader(): CustomLoader? {
        return if (loader != null) loader else CustomLoader().also { loader = it }
    }

}