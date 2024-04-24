package uk.ac.tees.mad.D3933743.ui.theme.all_tutors.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.D3933743.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun SelectedTutor(navController:NavController? = null) {
    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(title = {
            Text(text = "Tutor Details", modifier = Modifier.padding(start = 16.dp))
        },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.teal_200),
                titleContentColor = colorResource(id = R.color.white),
                actionIconContentColor = colorResource(id = R.color.white),
                navigationIconContentColor = colorResource(id = R.color.white)),
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(0.dp, 0.dp, 35.dp, 35.dp))
        ) {
            Card(shape = RectangleShape, modifier = Modifier.fillMaxWidth()) {

                Column(Modifier.padding(horizontal = 16.dp)) {
                    Image(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .size(125.dp, 125.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(
                            id = R.drawable.ic_launcher_background
                        ),
                        contentDescription = "Tutor Image"
                    )
                    Text(
                        text = "Tutor Name",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "I'm a passionate and experienced Mathematics tutor with more than 10 years of teaching experience. I have a Bachelor's degree in Mathematics and a Master's degree in Education, both from NYU. My expertise lies in teaching Mathematics, particularly Algebra and Geometry, to high school students.",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "Rating : 4.5 * ",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "Bio : Expertize in : Algebra, Trigonometry",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Check Availability", fontSize = 20.sp)
        }



        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Book Session", fontSize = 20.sp)
        }

    }


}

