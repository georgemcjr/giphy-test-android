package br.com.gj.giphytest.features.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.gj.giphytest.R
import br.com.gj.giphytest.features.trending.TrendingAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    // TODO DRY cases between this fragment and Trending

    private val viewModel: FavoritesViewModel by viewModel()

    private val adapter by lazy { TrendingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.favoritesLiveData.observe(viewLifecycleOwner, { favoriteList ->
            adapter.submitList(favoriteList)
            adapter.onSetItemFavorite = { item, isChecked, position ->
                if (!isChecked) {
                    viewModel.removeFavorite(item)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerView_favorites.layoutManager = GridLayoutManager(context, 2)
        recyclerView_favorites.adapter = adapter
    }
}