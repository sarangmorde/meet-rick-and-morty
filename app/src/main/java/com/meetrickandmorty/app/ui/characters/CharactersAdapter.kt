package com.meetrickandmorty.app.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.meetrickandmorty.app.BR
import com.meetrickandmorty.app.databinding.ItemCharactersListBinding
import com.meetrickandmorty.domain.model.Character

class CharactersAdapter(
    private val onCharactersItemClick: (Int, ImageView, Character) -> Unit
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

    fun setCharactersData(characters: List<Character>, isFiltering: Boolean = false) {
        this.characters.run {
            val position = size
            clearData()
            addAll(characters)
            if (position > characters.size || isFiltering) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeChanged(position, characters.size)
            }
        }
    }

    fun clearData() {
        characters.clear()
    }

    inner class CharactersListHolder(private val itemBinding: ItemCharactersListBinding) :
        RecyclerView.ViewHolder(
            itemBinding.root
        ) {

        fun bind(resultData: Character) {
            itemBinding.setVariable(BR.character, resultData)
            itemView.setOnClickListener {
                onCharactersItemClick(adapterPosition, itemBinding.imgCharacters, resultData)
            }
            ViewCompat.setTransitionName(itemBinding.imgCharacters, resultData.id.toString())
        }
    }
}