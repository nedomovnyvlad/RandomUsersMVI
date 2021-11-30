package com.vnedomovnyi.randomusersmvi.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vnedomovnyi.randomusersmvi.R

class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

}