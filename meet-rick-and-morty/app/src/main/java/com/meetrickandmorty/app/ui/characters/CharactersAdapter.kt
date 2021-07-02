package com.meetrickandmorty.app.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.meetrickandmorty.app.BR
import com.meetrickandmorty.app.R
import com.meetrickandmorty.app.databinding.ItemCharactersListBinding
import com.meetrickandmorty.domain.model.Character

class CharactersAdapter(
    private val onCharactersItemClick: (ImageView, Character) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.CharactersListHolder>() {

    private val characters: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListHolder {
        val binding = ItemCharactersListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CharactersListHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersListHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

    fun setCharactersData(characters: List<Character>, isFiltered: Boolean = false) {
        val position = this.characters.size
        if (isFiltered) {
            this.characters.clear()
        }
        this.characters.addAll(characters)
        if (isFiltered) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(position, characters.size)
        }
    }

    inner class CharactersListHolder(private val itemBinding: ItemCharactersListBinding) :
        RecyclerView.ViewHolder(
            itemBinding.root
        ) {

        fun bind(resultData: Character) {
            itemBinding.setVariable(BR.character, resultData)
            itemView.setOnClickListener {
                onCharactersItemClick(itemBinding.imgCharacters, resultData)
            }
            ViewCompat.setTransitionName(itemBinding.imgCharacters, resultData.id.toString())
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("characterImage")
        fun loadImage(view: ImageView, imageUrl: String) {
            val options = RequestOptions()
                .error(R.drawable.ic_rick_and_morty)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .placeholder(R.drawable.ic_rick_and_morty)
            Glide.with(view.context)
                .load(imageUrl).apply(options)
                .into(view)
        }
    }
}