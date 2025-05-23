package com.example.sampleapp.presentation.ui.component.item

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TitleText(text: String) {
    Text(
        text = AnnotatedString.fromHtml(text),
        style = MaterialTheme.typography.titleMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun BodyText(text: String) {
    Text(
        text = AnnotatedString.fromHtml(text),
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    underline: Boolean = false
) {
    Text(
        text = AnnotatedString.fromHtml(text),
        style = MaterialTheme.typography.labelMedium.copy(
            color = color,
            textDecoration = if (underline) {
                TextDecoration.Underline
            } else {
                TextDecoration.None
            }
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
fun DateText(date: Date) {
    Text(
        text = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(date),
        style = MaterialTheme.typography.labelSmall
    )
}

@Preview(showBackground = true)
@Composable
fun TitleTextPreview() {
    MaterialTheme {
        TitleText(
            text = "item 제목"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BodyTextPreview() {
    MaterialTheme {
        BodyText(
            text = "item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다." +
                    "tem 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다." +
                    "tem 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다. item 내용입니다."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LabelTextPreview() {
    MaterialTheme {
        LabelText(
            text = "item 라벨"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DateTextPreview() {
    MaterialTheme {
        DateText(date = Date())
    }
}
