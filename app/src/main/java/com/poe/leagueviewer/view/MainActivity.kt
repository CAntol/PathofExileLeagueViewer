package com.poe.leagueviewer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.poe.leagueviewer.R
import com.poe.leagueviewer.utils.KEY_TYPE
import kotlinx.android.synthetic.main.activity_main.*

private const val TYPE_MAIN = "main"
private const val TYPE_EVENT = "event"
private const val SELECTED_TYPE = "selectedType"

class MainActivity : FragmentActivity() {

    private var selectedType: String? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        if (selectedType == getType(item.itemId)) {
            return@OnNavigationItemSelectedListener true
        }

        val backStackCount = supportFragmentManager.backStackEntryCount
        for (i in 0..backStackCount) {
            supportFragmentManager.popBackStack()
        }

        when (item.itemId) {
            R.id.navigation_main -> {
                selectedType = TYPE_MAIN
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, getFragment(
                        TYPE_MAIN
                    ),
                    TYPE_MAIN
                ).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_event -> {
                selectedType = TYPE_EVENT
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, getFragment(
                        TYPE_EVENT
                    ),
                    TYPE_EVENT
                ).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        savedInstanceState?.getString(SELECTED_TYPE)?.let {
            selectedType = it
        }

        val type = selectedType ?: TYPE_MAIN
        navigation.selectedItemId = getItemId(type)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(SELECTED_TYPE, getType(navigation.selectedItemId))
        super.onSaveInstanceState(outState)
    }

    private fun getFragment(type: String) : Fragment {
        val frag = LeagueListFragment()
        val bundle = Bundle().apply { putString(KEY_TYPE, type) }
        frag.arguments = bundle
        return frag
    }

    private fun getItemId(type: String) : Int {
        return if (TYPE_EVENT == type) R.id.navigation_event else R.id.navigation_main
    }

    private fun getType(itemId: Int) : String {
        return if (R.id.navigation_event == itemId) TYPE_EVENT else TYPE_MAIN
    }
}
