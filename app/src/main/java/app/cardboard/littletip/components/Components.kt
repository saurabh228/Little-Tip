package app.cardboard.littletip.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cardboard.littletip.R
import app.cardboard.littletip.widgets.RoundButton
import kotlin.math.ceil

@Composable
fun TopHeader(ammount:Double = 0.0, persons: Int){
    Surface (modifier = Modifier
        .padding( 25.dp)
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(corner = CornerSize(20.dp)))
        .background(color = Color(0xFFFFFFFF)),
        color = colorResource(R.color.header)
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Total Per Person",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "₹"+"%.2f".format(ammount),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight(600)
            )

            Divider(modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp), thickness = 2.dp, color = Color.Black)

            Text(text = "Total Bill",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "₹"+"%.2f".format(ammount * persons ),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight(400)
            )
        }
    }
}


@Composable
fun  InputField(
        modifier: Modifier = Modifier,
        valueState: MutableState<String>,
        lableId: String,
        enabled: Boolean,
        isSingleLine: Boolean,
        keyboardType: KeyboardType = KeyboardType.Number,
        imeAction: ImeAction = ImeAction.Next,
        onAction: KeyboardActions = KeyboardActions.Default,
        onBillChange: () -> Unit = {
                }
    ){
    OutlinedTextField(value = valueState.value, 
                    onValueChange = {valueState.value = it
                                    onBillChange()},
                    label = {Text(text = lableId)},
                    leadingIcon = { Icon(painter = painterResource(id = R.drawable.indian_rupee_symbol), contentDescription = "Rupee Symbol" )},
                    singleLine = isSingleLine,
                    textStyle = TextStyle(fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.background),
                    modifier = modifier
                        .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
                                                        imeAction = imeAction),
                    keyboardActions = onAction
    )
    
}


@Composable
fun SpltIn (
//    modifier: Modifier =Modifier,
    persons: MutableIntState,
    onCountChange: () -> Unit = {

    }
){
    Row (modifier = Modifier.padding(horizontal = 35.dp),
        horizontalArrangement = Arrangement.Start){
        Text(text = "Spilt",
            modifier = Modifier.align(
                alignment = Alignment.CenterVertically),
            style = MaterialTheme.typography.bodyLarge
            )

        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ){
            RoundButton(
                imageVector = Icons.Default.Remove,
                onClick = {
                          if(persons.value > 1){
                              persons.value--
                              onCountChange()
                          }
                })

            Text(text = persons.value.toString(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 5.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold)

            RoundButton(
                imageVector = Icons.Default.Add,
                onClick = {
                    persons.value++
                    onCountChange()
                }
            )

        }
    }


}

@Composable
fun Tip (
    sliderState: MutableFloatState,
    billAmt: Double,
    onTipChange: () -> Unit = {

    }
){
    Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
    {


        Row(modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.Start)
        {
            Text(text = "Tip", modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyLarge)
            Row (modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                )
            {

                Text(text = "₹"+"%.2f".format(billAmt * sliderState.value),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 15.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold)

            }
        }

        Text(text = (ceil(sliderState.value * 10).toInt() * 10).toString()+"%",
            modifier = Modifier
                .padding( 15.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold)

        Slider(value = sliderState.value ,
                onValueChange = {
                    sliderState.value = it
                    onTipChange()
                },
                modifier = Modifier.padding(horizontal = 20.dp),
                steps = 9
            )

    }
}