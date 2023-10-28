package app.cardboard.littletip.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.cardboard.littletip.R

val Buttonsizemodifier = Modifier.padding(0.dp)

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick:()-> Unit,
    tint: Color = Color.Black.copy(alpha = 0.7f),
    backColor: Color = colorResource(R.color.billForm),
    elvation: Dp = 3.dp,
    ){
    Card(modifier = modifier.padding(10.dp)
        .clickable {onClick.invoke()}
        .then(Buttonsizemodifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = backColor
        ),
        elevation = CardDefaults.cardElevation(elvation)) {
        Icon(imageVector = imageVector, contentDescription ="plus or minus icon",
            tint = tint,
            modifier = Modifier.size(35.dp))

    }
}