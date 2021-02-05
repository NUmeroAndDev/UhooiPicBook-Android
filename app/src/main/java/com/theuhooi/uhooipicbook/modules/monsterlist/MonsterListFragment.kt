package com.theuhooi.uhooipicbook.modules.monsterlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterListViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.coil.CoilImage

@AndroidEntryPoint
class MonsterListFragment : Fragment() {

    // region Stored Instance Properties

    private var listener: OnListFragmentInteractionListener? = null

    private val viewModel: MonsterListViewModel by viewModels()

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding = FragmentMonsterListBinding.inflate(inflater, container, false)
        binding.monsterListRecyclerview.adapter =
            MonsterListRecyclerViewAdapter(
                this.listener,
                this.viewModel.monsters,
                this.viewLifecycleOwner
            )
        binding.monsterListRecyclerview.layoutManager = LinearLayoutManager(this.context)
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnListFragmentInteractionListener) {
            this.listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()

        this.listener = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_monster_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.licenses_menu_item -> {
                findNavController().navigate(MonsterListFragmentDirections.actionListToLicenses())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // endregion

    // region Interfaces

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: MonsterItem)
    }

    // endregion

}

@Composable
fun MonsterListItem(
    modifier: Modifier = Modifier,
    monsterItem: MonsterItem,
) {
    Card(
        modifier = modifier.clickable {
            // TODO navigate to detail
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                data = monsterItem.iconUrlString,
                contentDescription = null,
                modifier = Modifier.preferredSize(68.dp)
            )
            Spacer(modifier = Modifier.preferredWidth(16.dp))
            Text(
                text = monsterItem.name,
                style = AmbientTextStyle.current.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewMonsterListItem() {
    MonsterListItem(
        monsterItem = MonsterItem(
            name = "uhooi",
            iconUrlString = "https://placehold.jp/150x150.png"
        )
    )
}