package com.gmail.gabow95k.tmdb

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun setFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    id: Int
) {
    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    fragmentManager.beginTransaction()
        .replace(id, fragment, fragment.javaClass.name)
        .addToBackStack(fragment.javaClass.name)
        .commit()
}

fun addFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    idContent: Int
) {

    fragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        .add(idContent, fragment, fragment.javaClass.name)
        .addToBackStack(fragment.javaClass.name)
        .commit()
}