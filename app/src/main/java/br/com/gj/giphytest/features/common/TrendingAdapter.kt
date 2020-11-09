package br.com.gj.giphytest.features.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.gj.giphytest.R
import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.util.loadFromUrl
import kotlinx.android.synthetic.main.cell_item_gif.view.*

class TrendingAdapter :
    ListAdapter<GifItem, TrendingAdapter.TrendingItemViewHolder>(DiffCallback) {

    var onSetItemFavorite : ((GifItem, Boolean) -> Unit)? = null

    private object DiffCallback : DiffUtil.ItemCallback<GifItem>() {

        override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
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
                itemView.imageView_cell.loadFromUrl(item.gifUrl)

                val bgRes = if (item.isFavorite) {
                    R.drawable.toggle_selected_background
                } else {
                    R.drawable.toggle_unselected_background
                }

                itemView.button_favorite.setBackgroundResource(bgRes)
                itemView.button_favorite.setOnClickListener {
                    onSetItemFavorite?.invoke(item, !item.isFavorite)
                }
            }
        }
    }

}