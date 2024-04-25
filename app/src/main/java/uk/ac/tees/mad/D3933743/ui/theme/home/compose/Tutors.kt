package uk.ac.tees.mad.D3933743.ui.theme.home.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.home.data.Tutor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendedTutors(
    navController: NavController,
    list: List<Tutor>
) {

    val pagerState = rememberPagerState(pageCount = {
        list.size
    })

    HorizontalPager(state = pagerState, pageSize = PageSize.Fixed(250.dp)) { page ->
        RecommendedTutorItem(list[page],navController)
    }
}


@Composable
fun RecommendedTutorItem(tutor: Tutor, navController: NavController) {
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
                    painter = rememberAsyncImagePainter(tutor.profileUrl),
                    contentDescription = "Tutor Image"
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = tutor.name?:"", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = tutor.bio?:"", fontSize = 8.sp, lineHeight = 10.sp,
                        maxLines = 4,
                        modifier = Modifier.padding(top = 8.dp))
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
                    .height(30.dp),
                onClick = {
                    navController.navigate("TutorDetails/${tutor.id}")

                }) {
                Text(text = "View Details", fontSize = 10.sp)
            }
        }
    }
}


@Composable
fun AllTutors(navController: NavController, tutorList: List<Tutor>) {

    if (tutorList.isNotEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxWidth().padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(35.dp, 35.dp, 0.dp, 0.dp))
        ) {


            Card(shape = RectangleShape) {
                Column {
                    Text(
                        text = "See All Tutors",
                        textAlign = TextAlign.End,
                        color = Color.Blue,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 12.dp, end = 20.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("AllTutors")
                            }

                    )
                    LazyColumn(userScrollEnabled = false) {
                        items(10) { index ->
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
                                            painter =
                                            if (tutorList[index].profileUrl != null) {
                                                rememberAsyncImagePainter(tutorList[index].profileUrl)
                                            } else
                                                painterResource(id = R.drawable.ic_launcher_background),
                                            contentDescription = "Tutor Image"
                                        )
                                        Column(modifier = Modifier.padding(start = 16.dp)) {
                                            Text(
                                                text = tutorList[index].name!!,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(text = tutorList[index].bio!!, fontSize = 12.sp,
                                                lineHeight = 10.sp)
                                            Text(
                                                text = "Expert In : ${tutorList[index].subjects}",
                                                fontSize = 8.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


