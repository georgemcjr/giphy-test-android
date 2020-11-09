package br.com.gj.giphytest.features.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.gj.giphytest.R
import br.com.gj.giphytest.features.common.GifListAdapter
import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.features.common.model.State
import kotlinx.android.synthetic.main.fragment_trending.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment : Fragment() {

    private val viewModel: TrendingViewModel by viewModel()

    private val adapter by lazy { GifListAdapter() }

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
                    swipeRefresh_trending.isRefreshing = true
                }
                is State.Success<*> -> {
                    reloadItemsInAdapter(state.safeContent())
                    swipeRefresh_trending.isRefreshing = false
                }
                is State.Error -> {
                    Toast.makeText(context, state.error.message, Toast.LENGTH_LONG).show()
                    swipeRefresh_trending.isRefreshing = false
                }
            }
        })

    }

    private fun setupRecyclerView() {
        recyclerView_trending.layoutManager = GridLayoutManager(context, 2)
        recyclerView_trending.adapter = adapter
        adapter.onSetItemFavorite = { item, setFavorite ->
            if (setFavorite) {
                viewModel.addFavorite(item)
            } else {
                viewModel.removeFavorite(item)
            }
        }
    }

    private fun reloadItemsInAdapter(trendingItemList: List<GifItem>) {
        adapter.submitList(trendingItemList)
    }

    private fun setupRefresh() {
        swipeRefresh_trending.setOnRefreshListener {
            viewModel.fetchGifs(searchView_trending.query.toString())
        }
    }

    private fun setupSearchView() {
        searchView_trending.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.fetchGifs(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.fetchGifs()
                }
                return true
            }
        })
    }

    private fun loadData() {
        viewModel.fetchGifs()
    }

}