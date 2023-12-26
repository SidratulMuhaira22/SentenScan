package com.hera.sentenscan.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hera.sentenscan.R
import com.hera.sentenscan.ui.adapter.OnUserItemClickListener
import com.hera.sentenscan.ui.adapter.UserAdapter
import com.hera.sentenscan.data.api.ApiClient
import com.hera.sentenscan.data.api.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity(), OnUserItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        recyclerView = findViewById(R.id.user_recyclerview)
        userAdapter = UserAdapter(emptyList(), this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        userAdapter.setSelectedUserName("Selected User Name")

        val imgBack: ImageView = findViewById(R.id.ic_back)
        imgBack.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, ProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        val apiService = ApiClient.apiService
        val call = apiService.getUsers(page = 1, perPage = 10)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        userAdapter.updateData(it.users)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Unsuccessful response: $errorBody")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Network Error", "Network request failed", t)
            }
        })
    }

    override fun onUserItemClick(selectedUserName: String) {
        // Pindah ke ProfileActivity dengan membawa data
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("SELECTED_USER_NAME", selectedUserName)
        startActivity(intent)
    }
}
