package br.com.gj.giphytest.features.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.gj.giphytest.R
import br.com.gj.giphytest.model.GifItem
import br.com.gj.giphytest.util.loadFromUrl
import kotlinx.android.synthetic.main.cell_item_gif.view.*

class TrendingAdapter :
    ListAdapter<GifItem, TrendingAdapter.TrendingItemViewHolder>(DiffCallback) {

    var onSetItemFavorite : ((GifItem, Boolean) -> Unit)? = null

    private object DiffCallback : DiffUtil.ItemCallback<GifItem>() {

        override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingItemViewHolder {
        val viewFromLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_item_gif, parent, false)

        return TrendingItemViewHolder(viewFromLayout)
    }

    override fun onBindViewHolder(holder: TrendingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TrendingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: GifItem?) {
            if (item != null) {
                // TODO animate loading gif
                itemView.imageView_cell.loadFromUrl(item.gifUrl)
                itemView.button_favorite.isChecked = item.isFavorite
                itemView.button_favorite.setOnCheckedChangeListener { _, isChecked ->
                    onSetItemFavorite?.invoke(item, isChecked)
                }
            }
        }
    }

}