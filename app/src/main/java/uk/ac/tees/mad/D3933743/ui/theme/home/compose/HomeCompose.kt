package uk.ac.tees.mad.D3933743.ui.theme.home.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.home.data.MyCourseData

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeCompose(navController: NavController? = null) {

    Column {
        MyCourses(navController)
        RecommendedTutors(navController)
        AllTutors(navController)
    }

}


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

    val pagerState = rememberPagerState(pageCount = {
        4
    })

    HorizontalPager(state = pagerState) {
        MyCourseItem()
        MyCourseItem()
        MyCourseItem()
        MyCourseItem()
    }
}

@Composable
@Preview(showBackground = true)
fun MyCourseItem() {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Image(
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(
                        id = R.drawable.ic_launcher_background
                    ),
                    contentDescription = "Course Image"
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = "Course Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Description", fontSize = 8.sp)
                    Text(text = "Tutor", fontSize = 8.sp)
                    Text(text = "Duration : 3 Months", fontSize = 8.sp)
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
                    .height(30.dp),
                onClick = { }) {
                Text(text = "View Details", fontSize = 10.sp)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendedTutors(
    navController: NavController?,
    list: List<MyCourseData> = listOf(
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5"),
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5"),
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5"),
        MyCourseData("Title", "Desc", "Name", "id", "duration", true, "4.5")
    )
) {

    val pagerState = rememberPagerState(pageCount = {
        4
    })

    HorizontalPager(state = pagerState) {
        MyCourseItem()
        MyCourseItem()
        MyCourseItem()
        MyCourseItem()
    }
}


@Composable
@Preview(showBackground = true)
fun RecommendedTutorItem() {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Image(
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(
                        id = R.drawable.ic_launcher_background
                    ),
                    contentDescription = "Tutor Image"
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = "Tutor Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Bio", fontSize = 8.sp)
                    Text(text = "Expert In", fontSize = 8.sp)
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
                    .height(30.dp),
                onClick = { }) {
                Text(text = "View Details", fontSize = 10.sp)
            }
        }
    }
}


@Composable
@Preview
fun AllTutors(navController: NavController? = null) {

    Card() {

        Column {

            Text(
                text = "See All Tutors",
                textAlign = TextAlign.End,
                color = Color.Blue,
                modifier = Modifier
                    .padding(top = 8.dp, end = 12.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController?.navigate("AllTutors")
                    }

            )
            LazyColumn {
                items(4) {
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
                                    painter = painterResource(
                                        id = R.drawable.ic_launcher_background
                                    ),
                                    contentDescription = "Tutor Image"
                                )
                                Column(modifier = Modifier.padding(start = 16.dp)) {
                                    Text(
                                        text = "Tutor Name",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = "Bio", fontSize = 8.sp)
                                    Text(text = "Expert In", fontSize = 8.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
