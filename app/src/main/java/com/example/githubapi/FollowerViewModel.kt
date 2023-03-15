package com.example.githubapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    private val _users  = MutableLiveData<List<UserFollowerResponseItem>>()
    val users: LiveData<List<UserFollowerResponseItem>> = _users

    private val  _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    var username = String()

    companion object{
        private const val TAG = "FollowerViewModel"
        const val EXTRA_USERNAME = "username"
    }

    init {
        getFollower(username)
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<UserFollowerResponse> {
            override fun onResponse(
                call: Call<UserFollowerResponse>,
                response: Response<UserFollowerResponse>
            ){
                _isLoading.value = false
                if(response.isSuccessful){
                    _users.value = response.body()?.userFollowerResponse
                }else {
                    Log.e(FollowerViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserFollowerResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowerViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<UserFollowerResponse> {
            override fun onResponse(
                call: Call<UserFollowerResponse>,
                response: Response<UserFollowerResponse>
            ){
                _isLoading.value = false
                if(response.isSuccessful){
                    _users.value = response.body()?.userFollowerResponse
                }else {
                    Log.e(FollowerViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserFollowerResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowerViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}