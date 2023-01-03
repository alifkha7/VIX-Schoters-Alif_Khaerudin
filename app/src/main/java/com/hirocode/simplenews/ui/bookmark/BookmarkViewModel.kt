package com.hirocode.simplenews.ui.bookmark

import androidx.lifecycle.ViewModel
import com.hirocode.simplenews.data.NewsRepository

class BookmarkViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()
}