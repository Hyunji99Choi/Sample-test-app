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
import java.util.Date

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
        item = BlogItemData(
            title = "여름에 가기 좋은 실내 데이터 장소 추천",
            contents = "올 여름에는 꼭 가야 할 장소를 지금 당장 소개합니다. 서울 명소, 야경, 카페, 이색카페..",
            url = "https://example.com",
            dateTime = Date(),
            name = "여행 블로그",
            thumbnail = "https://example.com/150"
        )
    )
}