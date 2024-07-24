package com.example.footballer.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.footballer.data.DataSource.navigationItems
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.footballer.R
import com.example.footballer.ui.screens.fixture.FixtureDestination
import com.example.footballer.ui.screens.fixture.FixtureScreen
import com.example.footballer.ui.screens.home.HomeDestination
import com.example.footballer.ui.screens.home.HomeScreen
import com.example.footballer.ui.screens.table.TableDestination
import com.example.footballer.ui.screens.table.TableScreen
import com.example.footballer.ui.screens.team.TeamDestination
import com.example.footballer.ui.screens.team.TeamScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FootballerApp() {
    val navController = rememberNavController()
    FootballerNavHost(
        navController = navController
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FootballerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NavViewModel = viewModel(),
){
    var baseId = 2021

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = modifier.height(60.dp))
                navigationItems.forEachIndexed{index, item ->
                    NavigationDrawerItem(
                        label = { Text(stringResource(item.title))},
                        selected = index == viewModel.selectedItemIndex.value,
                        onClick = {
                            viewModel.setSelectedItemIndex(index)
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            if (item.route == HomeDestination.route){
                                navController.navigate(item.route)
                            }else{
                                navController.navigate("${item.route}/$baseId")
                            }
                        },
                        icon = {
                            Icon(
                                imageVector =  if (index ==  viewModel.selectedItemIndex.value) {
                                    item.selected
                                } else item.unselected,
                                contentDescription = stringResource(item.title)
                            )
                        },
                        modifier = modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopNav(
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                )
            },
            bottomBar = {
                BottomNav(
                    navigateHome = {navController.navigate(HomeDestination.route) },
                    navigateLeague = {navController.navigate("${TableDestination.route}/$baseId") },
                    viewModel = viewModel
                )
            },
        ){ innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HomeDestination.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = HomeDestination.route) {
                    HomeScreen(
                        onLeagueClick = {
                            navController.navigate("${TableDestination.route}/$it")
                            viewModel.setSelectedItemIndex(1)
                        },
                        onShareFixtureClick = { subject: String, text: String ->
                            shareData(context, subject, text)
                        },
                        onFixtureClick = {
                            navController.navigate("${FixtureDestination.route}/$it")
                            viewModel.setSelectedItemIndex(2)
                        }
                    )
                }
                composable(
                    route = TableDestination.routeWithArgs,
                    arguments = listOf(navArgument(TableDestination.leagueIdArg){
                        type = NavType.IntType
                    })
                ) {
                    TableScreen(
                        onShareTable = { subject: String, text: String ->
                            shareData(context, subject, text)
                        }
                    )
                }
                composable(
                    route = FixtureDestination.routeWithArgs,
                    arguments = listOf(navArgument(FixtureDestination.fixtureIdArg){
                        type = NavType.IntType
                    })
                ) {
                    FixtureScreen(
                        onTeamClick = {
                            navController.navigate("${TeamDestination.route}/$it")
                            viewModel.setSelectedItemIndex(2)
                        }
                    )
                }
                composable(
                    route = TeamDestination.routeWithArgs,
                    arguments = listOf(navArgument(TeamDestination.teamIdArg){
                        type = NavType.IntType
                    })
                ) {
                    TeamScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    navigateHome: () -> Unit,
    navigateLeague: () -> Unit,
    viewModel: NavViewModel
){
    NavigationBar(
        modifier = modifier,
        content = {
            navigationItems.forEachIndexed{index, item ->
                NavigationBarItem(
                    selected = index == viewModel.selectedItemIndex.value,
                    onClick = {
                        viewModel.setSelectedItemIndex(index)
                        if (item.route == HomeDestination.route){
                            navigateHome()
                        }else{
                            navigateLeague()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector =  if (index ==  viewModel.selectedItemIndex.value) {
                                item.selected
                            } else item.unselected,
                            contentDescription = stringResource(item.title)
                        )
                    }
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
){
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onMenuClick
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        },
        modifier = modifier
    )
}

private fun shareData(
    context: Context,
    subject: String,
    text: String
){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            subject
        )
    )
}
