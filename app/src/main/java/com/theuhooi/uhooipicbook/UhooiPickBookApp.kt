package com.theuhooi.uhooipicbook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ShareCompat
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.composethemeadapter.MdcTheme
import com.theuhooi.uhooipicbook.modules.monsterdetail.MonsterDetailScreen
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListScreen
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterListViewModel
import com.theuhooi.uhooipicbook.util.createTempPngFileUri
import kotlinx.coroutines.launch

@Composable
fun UhooiPicBookApp() {
    val navController = rememberNavController()

    MdcTheme {
        NavHost(navController = navController, startDestination = Screen.MonsterList.guide) {
            composable(
                route = Screen.MonsterList.guide
            ) {
                val context = LocalContext.current
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val viewModelFactory = remember(context) {
                    HiltViewModelFactory(context, checkNotNull(navBackStackEntry))
                }
                val monsterListViewModel: MonsterListViewModel = viewModel(factory = viewModelFactory)
                MonsterListScreen(
                    viewModel = monsterListViewModel,
                    onClickItem = {
                        navController.navigate(route = Screen.MonsterDetail.route(it))
                    },
                    onNavigateLicenses = {
                        navController.navigate(R.id.nav_oss_licenses)
                    }
                )
            }
            composable(
                route = Screen.MonsterDetail.guide,
                arguments = listOf(
                    navArgument(Screen.MonsterDetail.Argument.Name.key) {
                        type = NavType.StringType
                    },
                    navArgument(Screen.MonsterDetail.Argument.Description.key) {
                        type = NavType.StringType
                    },
                    navArgument(Screen.MonsterDetail.Argument.BaseColorCode.key) {
                        type = NavType.StringType
                    },
                    navArgument(Screen.MonsterDetail.Argument.IconUrl.key) {
                        type = NavType.StringType
                    },
                    navArgument(Screen.MonsterDetail.Argument.DancingUrl.key) {
                        type = NavType.StringType
                    },
                    navArgument(Screen.MonsterDetail.Argument.Order.key) {
                        type = NavType.IntType
                    },
                )
            ) {
                val monsterItem = MonsterItem(
                    name = checkNotNull(it.arguments?.getString(Screen.MonsterDetail.Argument.Name.key)),
                    description = checkNotNull(it.arguments?.getString(Screen.MonsterDetail.Argument.Description.key)),
                    baseColorCode = checkNotNull(it.arguments?.getString(Screen.MonsterDetail.Argument.BaseColorCode.key)),
                    iconUrlString = checkNotNull(it.arguments?.getString(Screen.MonsterDetail.Argument.IconUrl.key)),
                    dancingUrlString = checkNotNull(it.arguments?.getString(Screen.MonsterDetail.Argument.DancingUrl.key)),
                    order = checkNotNull(it.arguments?.getInt(Screen.MonsterDetail.Argument.Order.key)),
                )
                val context = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                MonsterDetailScreen(
                    monsterItem = monsterItem,
                    onBack = {
                        navController.popBackStack()
                    },
                    onShare = {
                        val request = ImageRequest.Builder(context)
                            .data(monsterItem.iconUrlString)
                            .target { drawable ->
                                ShareCompat.IntentBuilder(context)
                                    .setText(monsterItem.name + "\n" + monsterItem.unescapedDescription + "\n#UhooiPicBook")
                                    .setStream(context.createTempPngFileUri(drawable))
                                    .setType("image/png")
                                    .setChooserTitle(R.string.share_menu_item_title)
                                    .startChooser()
                            }
                            .build()
                        lifecycleOwner.lifecycleScope.launch {
                            ImageLoader(context).execute(request)
                        }
                    }
                )
            }
            activity(
                id = R.id.nav_oss_licenses
            ) {
                activityClass = OssLicensesMenuActivity::class
            }
        }
    }
}