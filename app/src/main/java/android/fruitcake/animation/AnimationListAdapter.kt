package android.fruitcake.animation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimationListAdapter(private val context: Context, private val items: List<AnimationListItem>) :
    RecyclerView.Adapter<AnimationListAdapter.AnimationViewHolder>() {

  override fun getItemCount(): Int = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimationViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(android.R.layout.simple_list_item_1, parent, false)
    return AnimationViewHolder(view)
  }

  override fun onBindViewHolder(holder: AnimationViewHolder, position: Int) {
    holder.title.text = items[position].title
    holder.setTitleOnClickListener(context, items)
  }

  class AnimationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(android.R.id.text1)

    fun setTitleOnClickListener(context: Context, items: List<AnimationListItem>) {
      title.setOnClickListener {
        RocketAnimationActivity.startActivity(context, items[adapterPosition].animationType)
      }
    }
  }
}
