package com.youxiang8727.streamlet.ui.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youxiang8727.streamlet.R
import kotlinx.coroutines.launch

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
    val offsetY = remember { Animatable(100f) }

    LaunchedEffect(Unit) {
        launch {
            alphaAnim.animateTo(1f, animationSpec = tween(1000))
        }
        launch {
            offsetY.animateTo(0f, animationSpec = tween(500))
            offsetY.animateTo(100f, animationSpec = tween(500))
            onFinish()
        }
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