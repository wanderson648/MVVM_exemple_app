package com.example.mvvmexemple.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmexemple.R
import com.example.mvvmexemple.models.Blog
import com.example.mvvmexemple.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class BlogAdapter(
    private val viewModel: MainViewModel,
    private val listBlog: List<Blog>,
    private val context: Context,
) : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.rv_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(blog = listBlog[position])
    }

    override fun getItemCount(): Int = listBlog.size

    inner class ViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(blog: Blog) {
            binding.findViewById<TextView>(R.id.txt_title_item).text = blog.title
            binding.findViewById<ImageButton>(R.id.btn_delete).setOnClickListener {
                setupAlertDialog(blog)
            }
        }
    }


    private fun setupAlertDialog(blog: Blog) {
        AlertDialog.Builder(context)
            .setMessage("Deseja exluir? ")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                viewModel.remove(blog)
                notifyItemRemoved(listBlog.indexOf(blog))
                Toast.makeText(context, "Excluído com sucesso", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create().show()
    }
}