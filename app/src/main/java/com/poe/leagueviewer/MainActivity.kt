package com.poe.leagueviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

private const val TYPE_MAIN = "main"
private const val TYPE_EVENT = "event"

class MainActivity : FragmentActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_main -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, getFragment(TYPE_MAIN), TYPE_MAIN).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_event -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, getFragment(TYPE_EVENT), TYPE_EVENT).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_main
    }

    private fun getFragment(type: String) : Fragment {
        val frag = LeagueFragment()
        val bundle = Bundle().apply { putString("type", type) }
        frag.arguments = bundle
        return frag
    }
}
