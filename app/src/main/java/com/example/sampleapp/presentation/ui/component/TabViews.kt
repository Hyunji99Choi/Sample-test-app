package com.example.sampleapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.domain.model.CategoryType
import com.example.sampleapp.domain.model.SortType

@Composable
fun <T> TabRow(
    items: List<T>,
    selected: T,
    onChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable (item: T, isSelected: Boolean, onClick: (T) -> Unit) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        items.forEach { item ->
            val isSelected = item == selected
            itemContent(item, isSelected) { onChange(item) }
        }
    }
}

/**
 * 전체, 블로그, 카페 .. 등등 카테고리 탭
 */
@Composable
fun ChipTabs(
    list: List<CategoryType>,
    selected: CategoryType,
    onChange: (CategoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        items = list,
        selected = selected,
        onChange = onChange,
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) { item, isSelected, onClick ->
        Text(
            text = stringResource(item.stringRes),
            modifier = Modifier
                .background(
                    if (isSelected) Color(0xFFEEEEEE) else Color(0xFFF8F8F8),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .clickable { onClick(item) },
            color = if (isSelected) Color.Black else Color.Gray
        )
    }
}

/**
 * 정확도순, 최신순 등등.. 정렬 탭
 */
@Composable
fun TextTabs(
    list: List<SortType>,
    selected: SortType,
    onChange: (SortType) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        items = list,
        selected = selected,
        onChange = onChange,
        modifier = modifier
    ) { item, isSelected, onClick ->
        Text(
            text = stringResource(item.stringRes),
            modifier = Modifier
                .padding(end = 30.dp)
                .clickable { onClick(item) },
            color = if (isSelected) Color.Black else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTabsPreview() {
    ChipTabs(
        list = CategoryType.entries.toList(),
        selected = CategoryType.ALL,
        onChange = {}
    )
}


@Preview(showBackground = true)
@Composable
fun SortTabsPreview() {
    TextTabs(
        list = SortType.entries.toList(),
        selected = SortType.ACCURACY,
        onChange = {}
    )
}
