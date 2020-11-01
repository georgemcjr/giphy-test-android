package br.com.gj.giphytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.gj.giphytest.features.favorites.FavoritesFragment
import br.com.gj.giphytest.features.trending.TrendingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewPager()
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf(
            TrendingFragment(),
            FavoritesFragment()
        )

        val viewPagerAdapter = MainPagerAdapter(fragmentList, supportFragmentManager, lifecycle)

        viewPager.adapter = viewPagerAdapter
    }
}