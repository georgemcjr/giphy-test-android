package br.com.gj.giphytest.features.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.gj.giphytest.R
import kotlinx.android.synthetic.main.fragment_trending.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment : Fragment() {

    private val viewModel: TrendingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trending, container, false)

        setupRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        viewModel.fetchTrendingGifs()
    }

    private fun setupObservers() {
        // TODO setup livedata observers
    }

    private fun setupRecyclerView() {
        recyclerView_trending.layoutManager = GridLayoutManager(context, 2)
    }

}