package br.com.gj.giphytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.gj.giphytest.features.favorites.FavoritesFragment
import br.com.gj.giphytest.features.trending.TrendingFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tabTitleResList = arrayListOf(R.string.title_trending, R.string.title_favorites)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf(
            TrendingFragment(),
            FavoritesFragment()
        )

        val viewPagerAdapter = MainPagerAdapter(fragmentList, supportFragmentManager, lifecycle)

        viewPager_main.adapter = viewPagerAdapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout_main, viewPager_main) { tab, position ->
            val stringRes = tabTitleResList[position]
            tab.text = getString(stringRes)
        }.attach()
    }
}