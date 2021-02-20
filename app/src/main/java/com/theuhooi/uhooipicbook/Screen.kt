package com.theuhooi.uhooipicbook

import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import java.net.URLEncoder

sealed class Screen(val path: String) {
    object MonsterList : Screen("monsterList")
    object MonsterDetail : Screen(
        "monsterDetail/{${Argument.Name.key}}/{${Argument.Description.key}}/{${Argument.BaseColorCode.key}}/{${Argument.IconUrl.key}}/{${Argument.DancingUrl.key}}/{${Argument.Order.key}}"
    ) {
        fun route(monsterItem: MonsterItem): String {
            return "monsterDetail/${monsterItem.name}/${monsterItem.description}/${monsterItem.baseColorCode}/${URLEncoder.encode(monsterItem.iconUrlString, "UTF-8")}/${URLEncoder.encode(monsterItem.dancingUrlString, "UTF-8")}/${monsterItem.order}"
        }

        enum class Argument(val key: String) {
            Name("Name"),
            Description("Description"),
            BaseColorCode("BaseColorCode"),
            IconUrl("IconUrl"),
            DancingUrl("DancingUrl"),
            Order("Order")
        }
    }
}