package app.cardboard.littletip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import app.cardboard.littletip.components.InputField
import app.cardboard.littletip.components.SpltIn
import app.cardboard.littletip.components.Tip
import app.cardboard.littletip.components.TopHeader
import app.cardboard.littletip.ui.theme.LittleTipTheme
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleTipTheme {
                // A surface container using the 'background' color from the theme
                MyApp {
                    val totalPerPerson = remember {
                        mutableDoubleStateOf(0.00)
                    }
                    val persons = remember {
                        mutableIntStateOf(1)
                    }
                    Column {
                        TopHeader(ammount = totalPerPerson.value, persons = persons.value)
                        BillForm(persons = persons){ billAmt ->
                            totalPerPerson.value = billAmt
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){
    Surface( modifier = Modifier.fillMaxSize(),

        color = colorResource(R.color.bakcground)
    ) {
        content()
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier,
             persons: MutableIntState,
             onValueChange: (Double) -> Unit = {
             }
    ){
    val billState = remember {
        mutableStateOf("")
    }
    val validState = remember(billState.value) {
        billState.value.trim().isNotEmpty()
    }
    val kbController = LocalSoftwareKeyboardController.current
    val billAmt = remember {
        mutableDoubleStateOf(0.00)
    }
//    val persons = remember {
//        mutableIntStateOf(1)
//    }
    val sliderState = remember {
        mutableFloatStateOf(0.0f)
    }

    Surface(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        colorResource(R.color.billForm),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column (modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start){
            InputField(valueState = billState,
                lableId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) {
                        onValueChange(0.0)
                        return@KeyboardActions
                    }
                    kbController?.hide()
//                    billAmt.value = billState.value.trim().toDouble()
//                    onValueChange((billAmt.value + (billAmt.value * (ceil(sliderState.value * 10).toInt()) )/10 )/persons.value)
                }){
                if(billState.value.trim().isNotEmpty()) {
                    billAmt.value = billState.value.trim().toDouble()
                    onValueChange((billAmt.value + (billAmt.value * (ceil(sliderState.value * 10).toInt()) / 10)) / persons.value)
                }else{
                    onValueChange(0.0)
                }
            }
            if(validState){

                    SpltIn(persons = persons){
                        onValueChange((billAmt.value + (billAmt.value * (ceil(sliderState.value * 10).toInt())/10 ))/persons.value)
                    }
                    Tip(sliderState = sliderState, billAmt = billAmt.value){
                        onValueChange((billAmt.value + (billAmt.value * (ceil(sliderState.value * 10).toInt())/10 ))/persons.value)
                    }
            }else{
                Box {

                }
            }
        }

    }

}
