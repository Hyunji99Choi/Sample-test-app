package com.example.sampleapp.presentation.ui.website

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.example.sampleapp.domain.model.CategoryType
import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.model.SortType
import com.example.sampleapp.domain.model.getFakeBlog
import com.example.sampleapp.domain.model.getFakeCafe
import com.example.sampleapp.presentation.ui.component.ChipTabs
import com.example.sampleapp.presentation.ui.component.SearchBar
import com.example.sampleapp.presentation.ui.component.TextTabs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun WebSiteScreen(
    viewModel: WebsiteViewModel = hiltViewModel()
) {
    WebSiteScreenContent(
        selectedSort = viewModel.selectedSort.collectAsState().value,
        selectedCategory = viewModel.selectedCategory.collectAsState().value,
        onQueryChange = viewModel::updateQuery,
        onSortChange = viewModel::updateSort,
        onCategoryChange = viewModel::updateCategory,
        mainListFlow = viewModel.mainList.collectAsState(initial = emptyList()).value,
        searchPagingFlow = viewModel.searchPaging
    )
}

@Composable
fun WebSiteScreenContent(
    selectedSort: SortType,
    selectedCategory: CategoryType,
    onQueryChange: (String) -> Unit,
    onSortChange: (SortType) -> Unit,
    onCategoryChange: (CategoryType) -> Unit,
    mainListFlow: List<List<ItemData>>,
    searchPagingFlow: Flow<PagingData<ItemData>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 검색창
        SearchBar(
            onSearch = { query ->
                onQueryChange(query)
            },
            modifier = Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        )

        // 정렬순
        TextTabs(
            list = SortType.entries.toList(),
            selected = selectedSort,
            onChange = onSortChange,
            modifier = Modifier.padding(20.dp)
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp
        )

        // 카테고리 탭
        ChipTabs(
            list = CategoryType.entries.toList(),
            selected = selectedCategory,
            onChange = onCategoryChange,
            modifier = Modifier.padding(16.dp)
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp
        )

        // 목록
        if (selectedCategory == CategoryType.ALL) {
            MainList(
                data = mainListFlow,
                onCategoryChange = onCategoryChange,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            TabListPaging(
                flow = searchPagingFlow,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WebSiteScreenPreview() {
    val fakeItems = listOf(
        getFakeBlog(),
        getFakeCafe(),
        getFakeBlog()
    )
    val fakePagingFlow = remember {
        MutableStateFlow(PagingData.from(fakeItems))
    }

    WebSiteScreenContent(
        selectedSort = SortType.ACCURACY,
        selectedCategory = CategoryType.WEB,
        onQueryChange = {},
        onSortChange = {},
        onCategoryChange = {},
        mainListFlow = listOf(),
        searchPagingFlow = fakePagingFlow
    )
}
