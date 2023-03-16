package com.example.githubapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followerSize = MutableLiveData<Int>()
    val followerSize: LiveData<Int> = _followerSize

    private val _followingSize = MutableLiveData<Int>()
    val followingSize: LiveData<Int> = _followingSize


    companion object {
        const val TAG = "DetailUserViewModel"
    }

    init {
        detailUser.value?.let { getUserDetail(it.login) }
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(DetailUserViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowerSize(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UserFollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowerResponseItem>>,
                response: Response<List<UserFollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Response body: Follower", response.body()?.size.toString())
                    _followerSize.value = response.body()?.size
                } else {
                    Log.e(DetailUserViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(
                call: retrofit2.Call<List<UserFollowerResponseItem>>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(DetailUserViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowingSize(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<UserFollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowerResponseItem>>,
                response: Response<List<UserFollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Response body: Following", response.body()?.size.toString())
                    _followingSize.value = response.body()?.size
                } else {
                    Log.e(DetailUserViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(
                call: retrofit2.Call<List<UserFollowerResponseItem>>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(DetailUserViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}