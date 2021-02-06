package com.theuhooi.uhooipicbook

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.color.MaterialColors
import com.theuhooi.uhooipicbook.extensions.IntColorInterface
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragmentDirections
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MonsterListFragment.OnListFragmentInteractionListener,
    IntColorInterface {

    // region View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        configureStatusBar()
    }

    // endregion

    // region Other Private Methods

    private fun configureStatusBar() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.monster_list_fragment) {
                this.window.statusBarColor = MaterialColors.getColor(
                    this,
                    R.attr.colorPrimaryVariant,
                    "colorPrimaryVariant is not set in the current theme"
                )
            }
        }
    }

    // endregion

    // region MonsterListFragment.OnListFragmentInteractionListener

    override fun onListFragmentInteraction(item: MonsterItem) {
        val action = MonsterListFragmentDirections.actionListToDetail(item)
        findNavController(R.id.nav_host_fragment).navigate(action)

        if (item.baseColorCode.isNotEmpty()) {
            val actionBarColor = Color.parseColor(item.baseColorCode)
            this.window.statusBarColor = actionBarColor.actionBarColorToStatusBarColor
        }
    }

    // endregion

}
