package com.vnedomovnyi.randomusersmvi.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vnedomovnyi.randomusersmvi.R
import com.vnedomovnyi.randomusersmvi.databinding.UserListFragmentBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class UserListFragment : Fragment() {

    private lateinit var binding: UserListFragmentBinding

    private val userListViewModel: UserListViewModel by stateViewModel()
    private val adapter = UserAdapter()
    private val onStopDisposables = CompositeDisposable()

    companion object {
        fun newInstance() = UserListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.user_list_fragment, container, false)
        binding = UserListFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.onItemClickListener = userListViewModel::deleteUser
    }

    override fun onStart() {
        super.onStart()
        userListViewModel.init(::render).addTo(onStopDisposables)
        userListViewModel.loadDataIfNeeded()
    }

    private fun render(viewState: UserListState) {
        with(viewState) {
            binding.progressBar.isVisible = viewState.isLoading
            binding.recyclerView.isVisible = users != null

            adapter.submitList(viewState.users ?: emptyList())

            error?.consume {
                showToast(R.string.error_message)
            }

            deletedUserEvent?.consume {
                showToast(R.string.delete_success_message)
            }
        }
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(requireContext(), resources.getText(messageResId), Toast.LENGTH_SHORT).show()
    }
}