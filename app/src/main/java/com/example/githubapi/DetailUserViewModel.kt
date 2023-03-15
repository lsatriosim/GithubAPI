package com.example.githubapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    private val _name = MutableLiveData<Any>()
    val name: LiveData<Any> = _name

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _avatarUrl = MutableLiveData<String>()
    val avatarUrl: LiveData<String> = _avatarUrl

    private val  _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    companion object{
        const val TAG = "DetailUserViewModel"
    }

    init {
        _userName.value?.let { getUserDetail(it) }
    }

    fun getUserDetail(username:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ){
                _isLoading.value = false
                if(response.isSuccessful){
                    _userName.value = response.body()?.login
                    _name.value = response.body()?.name
                    _avatarUrl.value = response.body()?.avatarUrl
                }else {
                    Log.e(DetailUserViewModel.TAG, "onSucces: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun setUserName(username: String){
        _userName.value = username
    }

}