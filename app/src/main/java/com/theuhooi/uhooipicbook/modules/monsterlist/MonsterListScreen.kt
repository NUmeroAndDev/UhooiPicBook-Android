package com.theuhooi.uhooipicbook.modules.monsterlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterListViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
fun MonsterListScreen(
    viewModel: MonsterListViewModel,
    onClickItem: (item: MonsterItem) -> Unit,
    onNavigateLicenses: () -> Unit
) {
    val monsterItemList by viewModel.monsters.observeAsState(emptyList())
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .background(MaterialTheme.colors.primaryVariant)
                .fillMaxWidth()
                .statusBarsHeight()
        )
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    backgroundColor = MaterialTheme.colors.primary,
                    actions = {
                        MonsterListPopupMenuAction(
                            onClickLicensesItem = onNavigateLicenses
                        )
                    }
                )
            },
            bodyContent = { innerPadding ->
                val modifier = Modifier.padding(innerPadding)
                MonsterListContent(
                    modifier = modifier,
                    monsterItemList = monsterItemList,
                    onClickItem = onClickItem
                )
            }
        )
    }
}


@Composable
fun MonsterListPopupMenuAction(
    onClickLicensesItem: () -> Unit,
) {
    var isShownDropdown by remember { mutableStateOf(false) }
    Box {
        IconButton(
            onClick = {
                isShownDropdown = true
            }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.more_action_description),
                tint = MaterialTheme.colors.onPrimary
            )
        }
        DropdownMenu(
            expanded = isShownDropdown,
            onDismissRequest = {
                isShownDropdown = false
            }
        ) {
            DropdownMenuItem(
                onClick = {
                    onClickLicensesItem()
                    isShownDropdown = false
                }
            ) {
                Text(text = stringResource(id = R.string.licenses_menu_item_title))
            }
        }
    }
}

@Composable
fun MonsterListContent(
    modifier: Modifier = Modifier,
    monsterItemList: List<MonsterItem>,
    onClickItem: (item: MonsterItem) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(monsterItemList) { index, item ->
            MonsterListItem(
                monsterItem = item,
                onClickItem = onClickItem
            )
            if (index < monsterItemList.lastIndex) {
                Spacer(modifier = Modifier.preferredHeight(16.dp))
            }
        }
    }
}

@Composable
fun MonsterListItem(
    modifier: Modifier = Modifier,
    monsterItem: MonsterItem,
    onClickItem: (item: MonsterItem) -> Unit,
) {
    Card(
        modifier = modifier.clickable {
            onClickItem(monsterItem)
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
                style = LocalTextStyle.current.copy(
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
        ),
        onClickItem = {}
    )
}