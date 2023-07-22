package com.example.projecteximap.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projecteximap.model.PostDetails
import com.example.projecteximap.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow

class FeedsDetailsViewModel(repoObject: Repository): ViewModel() {

    val getData = repoObject.getAllPosts()

    val commentsItem = MutableStateFlow<PostDetails?>(null)
}