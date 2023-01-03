package com.example.mvvmexemple

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmexemple.adapters.BlogAdapter
import com.example.mvvmexemple.models.Blog
import com.example.mvvmexemple.viewModels.MainViewModel
import com.example.mvvmexemple.viewModels.MainViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var mainRecycler: RecyclerView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecycler = findViewById(R.id.rv_items)
        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        btn = findViewById(R.id.btn_send)
        btn.setOnClickListener {
            addData()
        }

        initialiseAdapter()
    }

    private fun initialiseAdapter() {
        mainRecycler.layoutManager = viewManager
        observeData()
    }

    private fun observeData() {
        viewModel.list.observe(this, Observer {
            mainRecycler.adapter = BlogAdapter(viewModel, it, this)
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addData() {
        val txtTitle = findViewById<EditText>(R.id.edit_input_title)
        val title = txtTitle.text.toString()
        val txtError = findViewById<TextInputLayout>(R.id.til_input_title)

        txtError.error = if (title.isEmpty()) {
            getString(R.string.error_field_blank)
        } else {
            null
        }

        if (title.isNotEmpty()) {
            val blog = Blog(title)
            viewModel.add(blog)
            txtTitle.text.clear()
            mainRecycler.adapter?.notifyDataSetChanged()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val service = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}