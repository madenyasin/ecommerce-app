package com.yasinmaden.navigationss.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasinmaden.navigationss.R
import androidx.compose.material3.Button

@Composable
fun SocialMediaButton(
    modifier: Modifier, onClick: () -> Unit, image: Painter, name: String
) {
    Button(
        modifier = modifier.size(92.dp, 64.dp),
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White), // For Material3
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = image, contentDescription = name, modifier = Modifier.size(28.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SocialMediaButtonPreview() {

    SocialMediaButton(modifier = Modifier,
        image = painterResource(id = R.drawable.google_logo), // Replace with your sample image resource
        name = "Sample",
        onClick = {})


}