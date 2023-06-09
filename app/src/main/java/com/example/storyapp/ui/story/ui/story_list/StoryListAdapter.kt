package com.example.storyapp.ui.story.ui.story_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.storyapp.databinding.ItemsStoryBinding
import com.example.storyapp.response.ListStory

class StoryListAdapter(private val list: List<ListStory>): RecyclerView.Adapter<StoryListAdapter.StoryListViewHolder>() {
    interface OnItemClickCallback{
        fun onItemClicked(itemList : ListStory)
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class StoryListViewHolder(private val binding: ItemsStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemList: ListStory){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(itemList)
            }

            binding.apply {
                Glide.with(itemView.context).load(itemList.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(itemPic)
                itemDesc.text = itemList.description
                itemName.text = itemList.name
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
        val view = ItemsStoryBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return StoryListViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        holder.bind(list[position])
    }

}