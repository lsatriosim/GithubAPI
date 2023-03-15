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
    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var listFollower = ArrayList<String>()
    private var listFollowing = ArrayList<String>()

    companion object{
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

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        var parameterUserName = intent.getStringExtra(EXTRA_USERNAME)

        if(parameterUserName != null){
            viewModel.getUserDetail(parameterUserName)
        }

        viewModel.userName.observe(this, {userName ->
            setUserName(userName)
        })

        viewModel.name.observe(this, {name ->
            setName(name)
        })

        viewModel.avatarUrl.observe(this, {avatarUrl ->
            setAvatar(avatarUrl)
        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })


        val sectionsPagerAdapter = SectionsPagerAdapter(this, parameterUserName)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_CODES[position])
        }.attach()
    }

    private fun setAvatar(avatarUrl: String?) {
        Glide.with(this@DetailUserActivity)
            .load(avatarUrl)
            .into(binding.avatarPhoto)
    }

    private fun setUserName(username: String){
        binding.tvUsername.text = username
    }

    private fun setName(name: Any?){
        if(name != null){
            binding.tvName.text = name.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}