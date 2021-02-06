package com.theuhooi.uhooipicbook.modules.monsterlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterListViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun MonsterListScreen(
    viewModel: MonsterListViewModel
) {
    val monsterItemList by viewModel.monsters.observeAsState(emptyList())
    Scaffold(
        topBar = {
            // TODO impl menu item
            TopAppBar(
                title = { },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            MonsterListContent(
                modifier = modifier,
                monsterItemList = monsterItemList
            )
        }
    )
}

@Composable
fun MonsterListContent(
    modifier: Modifier = Modifier,
    monsterItemList: List<MonsterItem>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(monsterItemList) { index, item ->
            MonsterListItem(monsterItem = item)
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