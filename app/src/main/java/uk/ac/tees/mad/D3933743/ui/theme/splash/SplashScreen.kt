package uk.ac.tees.mad.D3933743.ui.theme.splash

import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.D3933743.R


@Composable
@Preview(showBackground = true)
fun SplashScreen(navController: NavController? = null) {

    val scale = remember {
        Animatable(0f)
    }


    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(5f).getInterpolation(it)
                })
        )
        delay(2000L)
        navController?.navigate("HomeScreen")
    }

    Box(
        modifier = Modifier
            .background(color = colorResource(id = R.color.teal_200))
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale.value)
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",

                )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Unlock your potential with QuickTutor \nYour shortcut to academic success!",
                fontSize = 18.sp,
                lineHeight = 28.sp,
                color = colorResource(id = R.color.white)
            )
        }

    }
}