package uk.ac.tees.mad.D3933743.ui.theme.home.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.D3933743.ui.theme.users.Course
import uk.ac.tees.mad.D3933743.ui.theme.users.User

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyCourses(
    list: User?
) {

    val pagerState = rememberPagerState(pageCount = { list?.course?.size ?: 0 })

    if ((list?.course?.size ?: 0) == 0) {

        Text(
            text = "No Course Found", modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

    } else {

        Column {
            HorizontalPager(state = pagerState, pageSize = PageSize.Fixed(200.dp)) { page ->
                MyCourseItem(list!!.course[page])
            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun MyCourseItem(
    myCourseData: Course? = null,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            Row {
                Image(
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                        .align(Alignment.CenterVertically),
                    painter = rememberAsyncImagePainter(myCourseData?.tutorImage),
                    contentDescription = "Tutor Image"
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = myCourseData?.tutorName!!,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = myCourseData.dateTime!!, fontSize = 8.sp)
                }
            }
        }
    }
}

