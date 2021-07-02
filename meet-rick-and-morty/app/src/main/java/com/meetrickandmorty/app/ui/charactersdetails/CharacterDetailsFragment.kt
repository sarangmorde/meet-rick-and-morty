package com.meetrickandmorty.app.ui.charactersdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.meetrickandmorty.app.BR
import com.meetrickandmorty.app.ui.MainActivity
import com.meetrickandmorty.app.R
import com.meetrickandmorty.app.databinding.FragmentCharacterDetailsBinding
import com.meetrickandmorty.app.databinding.FragmentCharacterDetailsBindingImpl
import com.meetrickandmorty.app.ui.base.BaseFragment

class CharacterDetailsFragment : BaseFragment() {

    private val args: CharacterDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBindingImpl.inflate(inflater)
        binding.apply {
            lifecycleOwner = this@CharacterDetailsFragment.viewLifecycleOwner
            setVariable(BR.character, this@CharacterDetailsFragment.args.character)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val character = args.character
        ViewCompat.setTransitionName(binding.ivCharacter, character.id.toString())
        (activity as MainActivity).supportActionBar?.title = character.name
        startPostponedEnterTransition()
    }
}