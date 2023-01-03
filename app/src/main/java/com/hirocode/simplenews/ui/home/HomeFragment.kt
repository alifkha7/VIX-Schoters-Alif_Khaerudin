package com.hirocode.simplenews.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hirocode.simplenews.R
import com.hirocode.simplenews.databinding.FragmentHomeBinding
import com.hirocode.simplenews.ui.NewsAdapter
import com.hirocode.simplenews.ui.ViewModelFactory
import com.hirocode.simplenews.ui.detail.NewsDetailActivity
import com.hirocode.simplenews.data.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels {
            factory
        }

        val newsAdapter = NewsAdapter { news ->
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.NEWS_DATA, news)
            startActivity(intent)
        }

        viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val newsData = result.data
                        newsAdapter.submitList(newsData)
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.viewError?.root?.visibility = View.VISIBLE
                        binding?.viewError?.tvError?.text = getString(R.string.something_wrong)
                    }
                }
            }

            binding?.rvNews?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}