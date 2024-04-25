package uk.ac.tees.mad.D3933743.ui.theme.home.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.D3933743.ui.theme.home.data.MyCourseData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyCourses(
    navController: NavController?,
    list: List<MyCourseData> = listOf(
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5"),
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5"),
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5"),
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5")
    )
) {

    val pagerState = rememberPagerState(pageCount = { 4 })


    Column {
        HorizontalPager(state = pagerState, pageSize = PageSize.Fixed(230.dp)) { page ->
            MyCourseItem(list[page])
        }
    }


}

@Composable
@Preview(showBackground = true)
fun MyCourseItem(
    myCourseData: MyCourseData = MyCourseData(
        "Title",
        "Desc",
        "Name",
        "id",
        "duration",
        true,
        "4.5"
    ),
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(text = "Course Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Description", fontSize = 8.sp)
                    Text(text = "Tutor", fontSize = 8.sp)
                    Text(text = "Duration : 3 Months", fontSize = 8.sp)
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                    .height(30.dp),
                onClick = { }) {
                Text(text = "View Details", fontSize = 10.sp)
            }
        }
    }
}

