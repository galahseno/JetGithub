package dev.seno.jetgithub.ui.screens.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.seno.jetgithub.R
import dev.seno.jetgithub.data.model.DetailUser
import dev.seno.jetgithub.ui.component.DisplayEmptyContentOrError
import dev.seno.jetgithub.ui.screens.detail.follow.FollowContent
import dev.seno.jetgithub.ui.theme.LARGE_PADDING
import dev.seno.jetgithub.ui.theme.ROUNDED_CORNER_SIZE
import dev.seno.jetgithub.ui.theme.SMALL_PADDING
import dev.seno.jetgithub.utils.Constants.getDetailTab
import dev.seno.jetgithub.utils.DisplayState
import dev.seno.jetgithub.utils.Resource
import kotlinx.coroutines.launch

@Composable
fun DetailContent(
    detailUser: Resource<DetailUser>,
    navigateToDetailScreen: (String) -> Unit,
    onScrollListener: (Int) -> Unit
) {
    when (detailUser) {
        is Resource.Success -> DetailUserSection(
            detailUser = detailUser.data,
            navigateToDetailScreen = navigateToDetailScreen,
            onScrollListener = onScrollListener
        )
        is Resource.Error -> DisplayEmptyContentOrError(DisplayState.Error)
        else -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
fun DetailUserSection(
    detailUser: DetailUser,
    navigateToDetailScreen: (String) -> Unit,
    onScrollListener: (Int) -> Unit
) {
    var fraction by remember { mutableStateOf(0.4f) }
    var alpha by remember { mutableStateOf(1f) }
    val alphaAnimation by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(500)
    )
    val fractionAnimate by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(700)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fractionAnimate)
                .alpha(alphaAnimation)
        ) {
            DetailContentTopSection(detailUser = detailUser)
        }
        DetailTabLayout(
            navigateToDetailScreen = navigateToDetailScreen,
            onScrollListener = { index ->
                fraction = if (index < 2) 0.4f else 0f
                alpha = if (index < 2) 1f else 0f
                onScrollListener(index)
            }
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailContentTopSection(
    detailUser: DetailUser
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(LARGE_PADDING),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = LARGE_PADDING * 2)
                .fillMaxWidth(0.4f)
                .clip(RoundedCornerShape(ROUNDED_CORNER_SIZE)),
        ) {
            Image(
                painter = rememberImagePainter(data = detailUser.avatarUrl),
                contentDescription = stringResource(id = R.string.user_image),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LARGE_PADDING),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.icon_name),
                    tint = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.width(SMALL_PADDING))
                Text(
                    text = detailUser.name ?: stringResource(id = R.string.no_data_placeholder),
                    color = MaterialTheme.colors.secondary
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(id = R.string.icon_name),
                    tint = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.width(SMALL_PADDING))
                Text(
                    text = detailUser.location ?: stringResource(id = R.string.no_data_placeholder),
                    color = MaterialTheme.colors.secondary
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.Work,
                    contentDescription = stringResource(id = R.string.icon_company),
                    tint = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.width(SMALL_PADDING))
                Text(
                    text = detailUser.company ?: stringResource(id = R.string.no_data_placeholder),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailTabLayout(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navigateToDetailScreen: (String) -> Unit,
    onScrollListener: (Int) -> Unit
) {
    val tabPage = getDetailTab()
    val pagerState = rememberPagerState(pageCount = tabPage.size)
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.secondary,
    ) {
        tabPage.forEachIndexed { index, page ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                text = {
                    Text(text = page.title)
                },
                icon = {
                    Icon(
                        imageVector = page.icon,
                        contentDescription = page.contentDescription
                    )
                }
            )
        }
    }

    val listFollowing by detailViewModel.listFollowingUser.collectAsState()
    val listFollower by detailViewModel.listFollowerUser.collectAsState()

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> FollowContent(
                listUser = listFollowing,
                navigateToDetailScreen = navigateToDetailScreen,
                onScrollListener = onScrollListener
            )
            1 -> FollowContent(
                listUser = listFollower,
                navigateToDetailScreen = navigateToDetailScreen,
                onScrollListener = onScrollListener
            )
        }
    }
}