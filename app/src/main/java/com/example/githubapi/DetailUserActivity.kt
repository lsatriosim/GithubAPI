package com.example.githubapi

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapi.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_NAME = "extra_name"

        @StringRes
        private val TAB_CODES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)
        var parameterUserName = intent.getStringExtra(EXTRA_USERNAME)

        if (parameterUserName != null) {
            viewModel.getUserDetail(parameterUserName)
            viewModel.getFollowerSize(parameterUserName)
            viewModel.getFollowingSize(parameterUserName)
        }

        viewModel.detailUser.observe(this) { userName ->
            setDetailUser(userName)
        }

        viewModel.followerSize.observe(this) { Followersize ->
            setFollowerSize(Followersize)
        }

        viewModel.followingSize.observe(this) { Followingsize ->
            setFollowingSize(Followingsize)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this, parameterUserName)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if (position == 1) {
                tab.text = resources.getString(TAB_CODES[position])
            } else {
                tab.text = resources.getString(TAB_CODES[position])
            }
        }.attach()
    }

    private fun setDetailUser(userName: DetailUserResponse?) {
        Glide.with(this@DetailUserActivity)
            .load(userName?.avatarUrl)
            .into(binding.avatarPhoto)
        binding.tvUsername.text = userName?.login
        binding.tvName.text = userName?.name.toString()
    }

    private fun setFollowingSize(followingsize: Int?) {
        binding.tvFolloingSize.text = followingsize.toString()
    }

    private fun setFollowerSize(followersize: Int?) {
        binding.tvFollowerSize.text = followersize.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}