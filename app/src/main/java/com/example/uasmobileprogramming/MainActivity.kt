package com.example.uasmobileprogramming

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uasmobileprogramming.adapters.EventAdapter
import com.example.uasmobileprogramming.databinding.ActivityMainBinding
import com.example.uasmobileprogramming.model.EventResponse
import com.example.uasmobileprogramming.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EventAdapter(mutableListOf()) // klik item sudah di-handle di adapter

        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapter

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, CreateEventActivity::class.java))
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadData()
        }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        ApiClient.instance.getAllEvents().enqueue(object : Callback<EventResponse> {

            override fun onResponse(call: Call<EventResponse>, resp: Response<EventResponse>) {
                binding.swipeRefresh.isRefreshing = false
                if (resp.isSuccessful && resp.body() != null) {
                    adapter.setData(resp.body()!!.data ?: emptyList())
                } else {
                    Toast.makeText(this@MainActivity, "Response error: ${resp.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(this@MainActivity, "Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
