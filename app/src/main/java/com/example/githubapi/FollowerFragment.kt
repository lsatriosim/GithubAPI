package com.example.githubapi

import android.os.Bundle
import android.text.style.TtsSpan.ARG_USERNAME
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapi.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private var listFollower = ArrayList<String>()
    private var listFollowing = ArrayList<String>()
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followerViewModel: FollowerViewModel
    var username: String? = null


    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var position = arguments?.getInt(ARG_POSITION, 0)
        var username = arguments?.getString(ARG_USERNAME)

        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())

        followerViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        followerViewModel.username = username.toString()

        followerViewModel.isLoading.observe(requireActivity()) { loading ->
            showLoading(loading)
        }

        if (position == 1) {
            followerViewModel.getFollower(username.toString())
            followerViewModel.users.observe(viewLifecycleOwner) {
                binding.rvFollower.layoutManager = LinearLayoutManager(requireActivity())

                for (user in it) {
                    listFollower.add(
                        """
                            ${user.avatarUrl};${user.login}
                        """.trimIndent()
                    )
                }

                for(follower in listFollower){
                    Log.d("follower", follower)
                }

                val adapter = UserAdapter(listFollower)
                binding.rvFollower.adapter = adapter
            }
        }else{
            followerViewModel.getFollowing(username.toString())
            followerViewModel.users.observe(viewLifecycleOwner) {
                binding.rvFollower.layoutManager = LinearLayoutManager(requireActivity())
                for (user in it) {
                    listFollower.add(
                        """
                            ${user.avatarUrl};${user.login}
                        """.trimIndent()
                    )
                }
                val adapter = UserAdapter(listFollower)
                binding.rvFollower.adapter = adapter
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
        } else {
            binding.followerProgressBar.visibility = View.VISIBLE
            binding.followerProgressBar.visibility = View.GONE
        }
    }
}