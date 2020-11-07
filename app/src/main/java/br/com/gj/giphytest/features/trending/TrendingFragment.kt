package br.com.gj.giphytest.features.trending

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.gj.giphytest.R
import br.com.gj.giphytest.model.GifItem
import br.com.gj.giphytest.model.State
import kotlinx.android.synthetic.main.fragment_trending.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment : Fragment() {

    private val viewModel: TrendingViewModel by viewModel()

    private val adapter by lazy { TrendingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupRefresh()
        setupSearchView()

        loadData()
    }

    private fun setupObservers() {
        viewModel.gifListLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Loading -> {
                    // TODO handle loading state
                    Log.d(">>>> New state", "Loading")
                    swipeRefresh_trending.isRefreshing = true
                }
                is State.Success<*> -> {
                    Log.d(">>>> New state", "Success: ${state.safeContent<List<GifItem>>()}")
                    loadTrendingItems(state.safeContent())
                    swipeRefresh_trending.isRefreshing = false
                }
                is State.Error -> {
                    // TODO handle error state
                    Log.d(">>>> New state", "Error: ${state.error}")
                    swipeRefresh_trending.isRefreshing = false
                }
            }
        })
    }

    private fun loadTrendingItems(trendingItemList: List<GifItem>) {
        adapter.submitList(trendingItemList)
        adapter.onSetItemFavorite = { item, isChecked ->
            if (isChecked) {
                viewModel.addFavorite(item)
            } else {
                viewModel.removeFavorite(item)
            }
        }

        recyclerView_trending.adapter = adapter
    }

    private fun setupRecyclerView() {
        recyclerView_trending.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setupRefresh() {
        swipeRefresh_trending.setOnRefreshListener {
            loadData()
        }
    }

    private fun setupSearchView() {
        searchView_trending.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(">>>>", " onQueryTextSubmit: $query")
                loadData()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(">>>>", " onQueryTextChange: $newText")
                if (newText.isNullOrBlank()) {
                    loadData()
                }
                return true
            }
        })
    }

    private fun loadData() {
        viewModel.fetchTrendingGifs()
    }

}