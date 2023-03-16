package com.example.githubapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val _follower = MutableLiveData<List<UserFollowerResponseItem>>()
    val follower: LiveData<List<UserFollowerResponseItem>> = _follower

    private val _following = MutableLiveData<List<UserFollowerResponseItem>>()
    val following: LiveData<List<UserFollowerResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var username = String()


    companion object {
        private const val TAG = "FollowerViewModel"
        const val EXTRA_USERNAME = "username"
    }

    init {
        getFollower(username)
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UserFollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowerResponseItem>>,
                response: Response<List<UserFollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Response body: Follower", response.body().toString())
                    _follower.value = response.body()
                } else {
                    Log.e(FollowerViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(
                call: retrofit2.Call<List<UserFollowerResponseItem>>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(FollowerViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<UserFollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowerResponseItem>>,
                response: Response<List<UserFollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Response body: Following", response.body().toString())
                    _following.value = response.body()
                } else {
                    Log.e(FollowerViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(
                call: retrofit2.Call<List<UserFollowerResponseItem>>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(FollowerViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}