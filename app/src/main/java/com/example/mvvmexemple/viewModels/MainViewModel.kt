package com.example.mvvmexemple.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmexemple.models.Blog

class MainViewModel: ViewModel() {
    var list = MutableLiveData<List<Blog>>()
    private var newList = mutableListOf<Blog>()

    fun add(blog: Blog) {
        newList.add(blog)
        list.value = newList
    }

    fun remove(blog: Blog) {
        newList.remove(blog)
        list.value = newList
    }

}