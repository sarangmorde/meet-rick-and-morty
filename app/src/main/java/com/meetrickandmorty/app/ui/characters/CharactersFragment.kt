package com.meetrickandmorty.app.ui.characters

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.transition.TransitionInflater
import com.meetrickandmorty.app.BR
import com.meetrickandmorty.app.R
import com.meetrickandmorty.app.databinding.FragmentCharactersBinding
import com.meetrickandmorty.app.databinding.FragmentCharactersBindingImpl
import com.meetrickandmorty.app.ui.MainActivity
import com.meetrickandmorty.app.ui.base.BaseFragment
import com.meetrickandmorty.app.utils.NetworkCheck
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.InfoModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment() {

    private var listScrollPosition: Int = 0
    private var isNavigatingBack: Boolean = false
    private var isFilteringCharacters = false
    private var nextPage = 1
    private var info: InfoModel? = null

    private val charactersAdapter: CharactersAdapter =
        CharactersAdapter { position, imageView, character ->
            listScrollPosition = position
            isNavigatingBack = true
            (activity as MainActivity).navigateToCharacterDetails(imageView, character)
        }

    private val viewModel: CharactersViewModel by viewModel()

    private lateinit var binding: FragmentCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedElementReturnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBindingImpl.inflate(inflater)
        binding.lifecycleOwner = this@CharactersFragment.viewLifecycleOwner
        viewModel.bindData(binding, BR.data)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        initRecyclerView()
        subscribeToViewModel()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun initRecyclerView() {
        binding.rvCharactersList.run {
            adapter = charactersAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == SCROLL_STATE_IDLE) {
                        if (!canScrollVertically(1)) {
                            viewModel.loadNextPage(nextPage, info)
                        }
                    }
                }
            })
            post {
                (layoutManager as GridLayoutManager).scrollToPosition(listScrollPosition)
            }
        }
    }

    private fun subscribeToViewModel() {
        viewModel.getCharactersLiveData().observe(viewLifecycleOwner, {
            if (isNavigatingBack) {
                isNavigatingBack = false
                return@observe
            }
            setCharactersData(it)
        })

        viewModel.getPaginationInfoLiveData().observe(viewLifecycleOwner, {
            info = it
            if (it.next.isNotEmpty()) {
                nextPage = Uri.parse(it.next).getQueryParameter(PAGE)?.toInt() ?: 1
            }
        })

        viewModel.getCharactersFailureLiveData().observe(viewLifecycleOwner, {
            if (isNavigatingBack) {
                isNavigatingBack = false
                return@observe
            }
            if (!NetworkCheck.isInternetConnected()) {
                showAlertMessage(
                    getString(R.string.lbl_internet_title),
                    getString(R.string.lbl_msg_no_internet_connection)
                )
            } else {
                showAlertMessage(getString(R.string.lbl_error_msg), it)
                NetworkCheck.observeForever { isConnected ->
                    if (isConnected) {
                        viewModel.loadNextPage(nextPage, info)
                    }
                }
            }
        })
    }

    private fun setCharactersData(characters: List<Character>) {
        charactersAdapter.setCharactersData(characters, isFilteringCharacters)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchViewItem: MenuItem = menu.findItem(R.id.menu_search)
        val searchView: SearchView = searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                isFilteringCharacters = true
                searchView.clearFocus()
                viewModel.filterCharacters(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.setOnCloseListener {
            isFilteringCharacters = false
            charactersAdapter.clearData()
            viewModel.filterCharacters(null)
            return@setOnCloseListener false
        }
    }

    companion object {
        private const val PAGE = "page"
    }
}