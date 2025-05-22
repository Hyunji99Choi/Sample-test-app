package com.example.sampleapp.presentation.ui.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.domain.model.WebItemData
import java.util.Date

@Composable
fun WebItem(item: WebItemData) {
    CoverView {
        Column(modifier = Modifier.padding(16.dp)) {
            TitleText(text = item.title)
            Spacer(modifier = Modifier.height(6.dp))

            BodyText(text = item.contents)
            Spacer(modifier = Modifier.height(8.dp))

            Row( // TODO url 길 경우 AND Hyperlink
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelText(
                    text = item.url,
                    color = Color(0xFF1E88E5),
                    underline = true
                )

                DateText(date = item.dateTime)
            }
        }
    }
}

@Preview
@Composable
fun WebItemPreview() {
    WebItem(
        item = WebItemData(
            title = "여름 장마 예보",
            contents = "2025년 여름 장마정선 정보.",
            url = "https://example.com/news",
            dateTime = Date()
        )
    )
}
