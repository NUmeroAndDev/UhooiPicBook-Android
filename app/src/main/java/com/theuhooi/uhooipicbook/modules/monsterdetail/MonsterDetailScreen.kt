package com.theuhooi.uhooipicbook.modules.monsterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.extensions.actionBarColorToStatusBarColor
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
fun MonsterDetailScreen(
    monsterItem: MonsterItem,
    onBack: () -> Unit,
    onShare: () -> Unit,
) {
    val statusBarColor = if (monsterItem.baseColorCode.isEmpty()) {
        MaterialTheme.colors.primaryVariant
    } else {
        Color(android.graphics.Color.parseColor(monsterItem.baseColorCode).actionBarColorToStatusBarColor)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .background(statusBarColor)
                .fillMaxWidth()
                .statusBarsHeight()
        )
        Scaffold(
            topBar = {
                val appBarColor = if (monsterItem.baseColorCode.isEmpty()) {
                    MaterialTheme.colors.primary
                } else {
                    Color(android.graphics.Color.parseColor(monsterItem.baseColorCode))
                }
                TopAppBar(
                    title = { },
                    backgroundColor = appBarColor,
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.navigate_up_description),
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onShare) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = stringResource(id = R.string.share_menu_item_title),
                            )
                        }
                    }
                )
            },
            bodyContent = { innerPadding ->
                val modifier = Modifier.padding(innerPadding)
                MonsterDetailContent(
                    modifier = modifier,
                    monsterItem = monsterItem
                )
            }
        )
    }
}

@Composable
fun MonsterDetailContent(
    modifier: Modifier = Modifier,
    monsterItem: MonsterItem,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        MonsterDetail(monsterItem = monsterItem)
    }
}

@Composable
fun MonsterDetail(
    modifier: Modifier = Modifier,
    monsterItem: MonsterItem,
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .padding(top = 56.dp)
    ) {
        CoilImage(
            modifier = Modifier
                .preferredSize(240.dp)
                .align(Alignment.CenterHorizontally),
            data = monsterItem.iconUrlString,
            contentDescription = null
        )
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        CoilImage(
            modifier = Modifier
                .preferredSize(48.dp)
                .align(Alignment.End),
            data = monsterItem.dancingUrlString,
            contentDescription = null
        )
        Spacer(modifier = Modifier.preferredHeight(32.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = monsterItem.name,
            style = LocalTextStyle.current.copy(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.preferredHeight(64.dp))
        Providers(LocalContentAlpha provides ContentAlpha.medium) {
            // FIXME workaround of newline code
            val text = monsterItem.description.replace("\\n", "\n")
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = text,
                style = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewMonsterDetail() {
    MonsterDetail(
        monsterItem = MonsterItem(
            name = "uhooi",
            iconUrlString = "https://placehold.jp/150x150.png",
            description = "description"
        )
    )
}