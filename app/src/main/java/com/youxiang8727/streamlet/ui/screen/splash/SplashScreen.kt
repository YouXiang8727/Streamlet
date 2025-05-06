package com.youxiang8727.streamlet.ui.screen.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import com.youxiang8727.streamlet.R

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun SplashScreen(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {}
) {
    val context = LocalContext.current

    val alphaAnim = remember { Animatable(0f) }
    val offsetY = remember { Animatable(20f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(1f, animationSpec = tween(1000))
        offsetY.animateTo(0f, animationSpec = tween(1000))
        delay(1500)
        onFinish()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(96.dp)
                    .graphicsLayer {
                        alpha = alphaAnim.value
                        translationY = offsetY.value
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = context.getText(R.string.app_name).toString(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.graphicsLayer {
                    alpha = alphaAnim.value
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = context.getText(R.string.app_subtitle).toString(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                ),
                modifier = Modifier.graphicsLayer {
                    alpha = alphaAnim.value
                }
            )
        }
    }
}