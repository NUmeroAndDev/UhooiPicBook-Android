package com.theuhooi.uhooipicbook.modules.monsterdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import dev.chrisbanes.accompanist.coil.CoilImage

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
            style = AmbientTextStyle.current.copy(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.preferredHeight(64.dp))
        Providers(AmbientContentAlpha provides ContentAlpha.medium) {
            // FIXME workaround of newline code
            val text = monsterItem.description.replace("\\n", "\n")
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = text,
                style = AmbientTextStyle.current.copy(
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