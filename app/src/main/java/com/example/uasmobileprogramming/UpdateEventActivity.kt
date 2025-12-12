package com.example.uasmobileprogramming

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uasmobileprogramming.databinding.ActivityUpdateEventBinding
import com.example.uasmobileprogramming.model.EventRequest
import com.example.uasmobileprogramming.network.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class UpdateEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateEventBinding
    private var eventId = ""
    private val statusOptions = listOf("upcoming", "ongoing", "completed", "cancelled")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getStringExtra("id") ?: ""

        binding.etTitle.setText(intent.getStringExtra("title"))
        binding.etDate.setText(intent.getStringExtra("date"))
        binding.etTime.setText(intent.getStringExtra("time"))
        binding.etLocation.setText(intent.getStringExtra("location"))
        binding.etDescription.setText(intent.getStringExtra("description"))
        binding.etCapacity.setText(intent.getStringExtra("capacity"))

        setupStatusSpinner()

        binding.etDate.setOnClickListener { showDatePicker() }
        binding.etTime.setOnClickListener { showTimePicker() }

        binding.btnUpdate.setOnClickListener { if (valid()) updateEvent() }
        binding.btnDelete.setOnClickListener { deleteEvent() }
    }

    private fun setupStatusSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spStatus.adapter = adapter

        // Set default status dari intent
        val currentStatus = intent.getStringExtra("status") ?: "upcoming"
        val index = statusOptions.indexOf(currentStatus)
        if (index >= 0) binding.spStatus.setSelection(index)
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            binding.etDate.setText("%04d-%02d-%02d".format(year, month + 1, day))
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePicker() {
        val cal = Calendar.getInstance()
        TimePickerDialog(this, { _, hour, minute ->
            binding.etTime.setText("%02d:%02d:00".format(hour, minute))
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    private fun valid(): Boolean {
        if (eventId.isEmpty()) {
            Toast.makeText(this, "Event ID tidak tersedia", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etTitle.text.toString().trim().isEmpty()) {
            binding.etTitle.error = "Wajib diisi"
            return false
        }
        if (binding.etDate.text.toString().trim().isEmpty()) {
            binding.etDate.error = "Pilih tanggal"
            return false
        }
        if (binding.etTime.text.toString().trim().isEmpty()) {
            binding.etTime.error = "Pilih waktu"
            return false
        }
        if (binding.etLocation.text.toString().trim().isEmpty()) {
            binding.etLocation.error = "Wajib diisi"
            return false
        }
        return true
    }

    private fun updateEvent() {
        val event = EventRequest(
            title = binding.etTitle.text.toString().trim(),
            date = binding.etDate.text.toString().trim(),
            time = binding.etTime.text.toString().trim(),
            location = binding.etLocation.text.toString().trim(),
            description = binding.etDescription.text.toString().trim(),
            capacity = binding.etCapacity.text.toString().toIntOrNull(),
            status = binding.spStatus.selectedItem.toString()
        )

        ApiClient.instance.updateEvent(eventId, event).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val msg = response.body()?.string() ?: "Event berhasil diupdate"
                    Toast.makeText(this@UpdateEventActivity, "Event berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val err = response.errorBody()?.string()
                    Toast.makeText(this@UpdateEventActivity, "Error: $err", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@UpdateEventActivity, "Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteEvent() {
        if (eventId.isEmpty()) {
            Toast.makeText(this, "Event ID tidak tersedia", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClient.instance.deleteEvent(eventId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val msg = "Event berhasil dihapus"
                    Toast.makeText(this@UpdateEventActivity, msg, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val err = response.errorBody()?.string()
                    Toast.makeText(this@UpdateEventActivity, "Error: $err", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@UpdateEventActivity, "Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
