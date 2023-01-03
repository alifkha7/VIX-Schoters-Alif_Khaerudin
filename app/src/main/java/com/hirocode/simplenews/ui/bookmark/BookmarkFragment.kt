package com.hirocode.simplenews.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hirocode.simplenews.R
import com.hirocode.simplenews.databinding.FragmentBookmarkBinding
import com.hirocode.simplenews.ui.NewsAdapter
import com.hirocode.simplenews.ui.ViewModelFactory
import com.hirocode.simplenews.ui.detail.NewsDetailActivity

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: BookmarkViewModel by viewModels {
            factory
        }

        val newsAdapter = NewsAdapter { news ->
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.NEWS_DATA, news)
            startActivity(intent)
        }

        viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
            newsAdapter.submitList(bookmarkedNews)
            binding?.progressBar?.visibility = View.GONE
            binding?.viewError?.tvError?.text = getString(R.string.no_data)
            binding?.viewError?.root?.visibility =
                if (bookmarkedNews.isNotEmpty()) View.GONE else View.VISIBLE
        }

        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}