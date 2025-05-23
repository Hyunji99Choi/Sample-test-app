package com.example.sampleapp.presentation.ui.website

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sampleapp.R
import com.example.sampleapp.domain.model.BlogItemData
import com.example.sampleapp.domain.model.CafeItemData
import com.example.sampleapp.domain.model.CategoryType
import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.model.WebItemData
import com.example.sampleapp.domain.model.getFakeBlog
import com.example.sampleapp.domain.model.getFakeCafe
import com.example.sampleapp.domain.model.getFakeWeb
import com.example.sampleapp.presentation.ui.component.item.BlogItem
import com.example.sampleapp.presentation.ui.component.item.CafeItem
import com.example.sampleapp.presentation.ui.component.item.WebItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ItemData.Render() {
    when (this) {
        is BlogItemData -> BlogItem(this)
        is CafeItemData -> CafeItem(this)
        is WebItemData -> WebItem(this)
    }
}


@Composable
fun MainList(
    data: List<List<ItemData>>,
    onCategoryChange: (CategoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {

        data.forEach { group ->
            val type = group.firstOrNull() ?: return@forEach
            val (category, textRes) = when (type) {
                is BlogItemData -> {
                    CategoryType.BLOG to R.string.category_blog
                }

                is CafeItemData -> {
                    CategoryType.CAFE to R.string.category_cafe
                }

                is WebItemData -> {
                    CategoryType.WEB to R.string.category_web
                }
            }

            // title
            item {
                Text(
                    stringResource(textRes),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            items(group) { item ->
                item.Render()
            }

            // "더보기" 버튼
            item {
                Text(
                    text = stringResource(R.string.see_more_text),
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            onCategoryChange(category)
                        })
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )

            }
        }
    }
}

@Composable
fun TabListPaging(
    flow: Flow<PagingData<ItemData>>,
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(count = lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.Render()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainListPreview() {
    val fakeItems = listOf(
        listOf(
            getFakeBlog(),
            getFakeBlog()
        ),
        listOf(
            getFakeCafe()
        ),
        listOf(
            getFakeWeb()
        )
    )

    MainList(data = fakeItems, onCategoryChange = {})
}


@Preview(showBackground = true)
@Composable
fun TabListPagingPreview() {
    val fakeItems = listOf(
        getFakeBlog(),
        getFakeBlog(),
        getFakeBlog(),
        getFakeCafe(),
        getFakeWeb()
    )

    val fakePagingFlow = remember {
        MutableStateFlow(PagingData.from(fakeItems))
    }

    TabListPaging(flow = fakePagingFlow)
}
