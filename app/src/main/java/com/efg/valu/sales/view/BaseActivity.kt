package com.efg.valu.sales.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    fun notifyFragmentsAppearing() {
        val currentFragment: Fragment? = getCurrentFragment()
        if (currentFragment is BaseFragment) {
            currentFragment.didAppear()
        }
    }

    open fun getCurrentFragment(): Fragment? {
        return if (getContainerId() != -1)
            supportFragmentManager
                .findFragmentById(getContainerId()) else null
    }

    open fun getContainerId(): Int = -1
}