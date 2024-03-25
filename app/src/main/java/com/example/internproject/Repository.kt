package com.example.internproject

import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository () {

    private val twitchRetrofitInstance = TwitchRetrofitInstance.apiService()

   fun getUsers(authorizationToken: String, clientId: String, id: String, textView: TextView) {
        twitchRetrofitInstance.getUsers("Bearer $authorizationToken", clientId, id).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val successMessage = "GET Success: ${response.code()}"
                    textView.text = successMessage
                } else {
                    val errorMessage = "GET Error: ${response.code()}"
                    textView.text = errorMessage
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.i("TAGS", "GET Failure: ${t.message}")
                textView.append("GET Failure: ${t.message}")
            }
        })
    }

}