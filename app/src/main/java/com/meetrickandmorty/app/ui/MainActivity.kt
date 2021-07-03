package com.meetrickandmorty.app.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.ui.setupActionBarWithNavController
import com.meetrickandmorty.app.R
import com.meetrickandmorty.app.ui.characters.CharactersFragmentDirections
import com.meetrickandmorty.domain.model.Character


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, null)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.purple_500
                )
            )
        )
    }

    fun navigateToCharacterDetails(imageView: ImageView, character: Character) {
        navController.navigate(
            CharactersFragmentDirections.navigateToCharacterDetails(character),
            FragmentNavigatorExtras(
                imageView to character.id.toString()
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}