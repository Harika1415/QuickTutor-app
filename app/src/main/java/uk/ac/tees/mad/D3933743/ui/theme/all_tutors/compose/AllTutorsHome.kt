package uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.D3933743.R
import uk.ac.tees.mad.D3933743.ui.theme.all_tutors.data.TutorDetails
import uk.ac.tees.mad.D3933743.ui.theme.home.compose.AllTutors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTutorsHome(navController: NavController? = null, tutorsList: List<TutorDetails>) {


    Column(modifier = Modifier.background(color = colorResource(id = R.color.white))) {

        TopAppBar(title = {
            Text(text = "Tutors", modifier = Modifier.padding(start = 16.dp))
        },
            windowInsets = WindowInsets(top = 0.dp),
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            navController?.popBackStack()
                        },
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back Arrow"
                )
            })

        LazyColumn {
            items(tutorsList.size) { index ->
                ElevatedCard(
                    modifier = Modifier
                        .clickable {
                            navController?.navigate("TutorDetails")
                        }
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(6.dp)) {
                        Row {
                            Image(
                                modifier = Modifier
                                    .size(50.dp, 50.dp)
                                    .align(Alignment.CenterVertically),
                                painter = rememberAsyncImagePainter(tutorsList[index].tutorImage),
                                contentDescription = "Tutor Image"
                            )
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    text = tutorsList[index].tutorName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = tutorsList[index].tutorBio, fontSize = 8.sp)
                                Text(text = tutorsList[index].tutorExpertise, fontSize = 8.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun AllTutorsHomePreview() {
    AllTutorsHome(
        tutorsList = listOf(
            TutorDetails(
                "Lady Margaret's",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                "Divinity"

            ),
            TutorDetails(
                "Lady Margaret's",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                "Divinity"

            ),
            TutorDetails(
                "Lady Margaret's",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                "Divinity"

            ),
            TutorDetails(
                "Lady Margaret's",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Lady_Margaret_Beaufort_from_NPG.jpg/956px-Lady_Margaret_Beaufort_from_NPG.jpg",
                "Portrait of Margaret Beaufort (1443-1509) dressed as a widow, mother of Henry VII of England. The portrait features the coat of arms of the House of Beaufort, and the words \"souvent me souvient\", Medieval French for \"think of me often\", now used as the motto of Lady Margaret Hall, Oxford, Christ's College, Cambridge and St John's College, Cambridge.",
                "Divinity"

            ),
        )
    )
}

