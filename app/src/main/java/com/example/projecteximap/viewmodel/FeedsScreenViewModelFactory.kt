package com.example.projecteximap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projecteximap.api.GRPCResponse
import com.example.projecteximap.repository.Repository

class FeedsScreenViewModelFactory(var repoObj: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedsDetailsViewModel(repoObj) as T
    }
}