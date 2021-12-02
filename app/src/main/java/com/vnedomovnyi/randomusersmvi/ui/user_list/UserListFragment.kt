package com.vnedomovnyi.randomusersmvi.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vnedomovnyi.randomusersmvi.R
import com.vnedomovnyi.randomusersmvi.ui.base.mviViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import timber.log.Timber

class UserListFragment : Fragment() {

    private val userListViewModel: UserListViewModel by mviViewModel()
    private val onStopDisposables = CompositeDisposable()

    companion object {
        fun newInstance() = UserListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        userListViewModel.init(::render).addTo(onStopDisposables)
        userListViewModel.loadDataIfNeeded()
    }

    private fun render(viewState: UserListState) {
        with(viewState) {
            // TODO: Add displaying users
            Timber.d(viewState.users.toString())

            error?.consume {
                Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }
}