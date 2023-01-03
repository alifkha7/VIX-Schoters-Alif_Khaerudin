package com.hirocode.simplenews.ui.home

import androidx.lifecycle.ViewModel
import com.hirocode.simplenews.data.NewsRepository

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()
}