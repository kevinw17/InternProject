package com.example.internproject

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val repository: Repository) : ViewModel() {

    private val _result = MutableLiveData<Users>(null)
    val result: LiveData<Users> = _result

    private val twitchRetrofitInstance = TwitchRetrofitInstance.apiService()

//    fun getUsers(authorizationToken: String, clientId: String, id: String) {
//        twitchRetrofitInstance.getUsers("Bearer $authorizationToken", clientId, id).enqueue(object : Callback<Users> {
//            override fun onResponse(call: Call<Users>, response: Response<Users>) {
//                if (response.isSuccessful) {
//                    _result.value = response.body()
//                    Log.i("TAGS", "GET Success : ${response.code()}")
//                } else {
//                    Log.i("TAGS", "GET Error: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<Users>, t: Throwable) {
//                Log.i("TAGS", "GET Failure: ${t.message}")
//            }
//        })
//    }

}