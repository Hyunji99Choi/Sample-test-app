package com.example.sampleapp.presentation.ui.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.domain.model.BlogItemData
import com.example.sampleapp.domain.model.getFakeBlog

@Composable
fun BlogItem(item: BlogItemData) {
    CoverView {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ThumbnailView(
                url = item.thumbnail,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                TitleText(text = item.title)
                Spacer(modifier = Modifier.height(6.dp))

                BodyText(text = item.contents)
                Spacer(modifier = Modifier.height(8.dp))

                LabelText(text = item.name)
                DateText(date = item.dateTime)
            }
        }
    }
}

@Preview
@Composable
fun BlogItemPreview() {
    BlogItem(
        item = getFakeBlog()
    )
}