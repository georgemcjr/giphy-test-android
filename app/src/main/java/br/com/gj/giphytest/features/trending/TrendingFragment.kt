package br.com.gj.giphytest.features.trending

import android.os.Bundle
import android.util.Log
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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupObservers()

        viewModel.fetchTrendingGifs()
    }

    private fun setupObservers() {
        viewModel.gifListLiveData.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Loading -> {
                    // TODO handle loading state
                    Log.d(">>>> New state", "Loading")
                }
                is State.Success<*> -> {
                    Log.d(">>>> New state", "Success: ${state.content}")
                }
                is State.Error -> {
                    // TODO handle error state
                    Log.d(">>>> New state", "Error: ${state.error}")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerView_trending.layoutManager = GridLayoutManager(context, 2)
    }

}