package com.vnedomovnyi.randomusersmvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vnedomovnyi.randomusersmvi.ui.user_list.UserListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserListFragment.newInstance())
                .commitNow()
        }
    }
}