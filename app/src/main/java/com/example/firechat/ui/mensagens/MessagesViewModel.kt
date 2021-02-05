package com.example.firechat.ui.mensagens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessagesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Mensagens Fragment"
    }
    val text: LiveData<String> = _text
}