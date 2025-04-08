package br.com.zafcode.atividade1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zafcode.atividade1.ui.theme.Atividade1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Atividade1Theme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val currentExercicio = remember { mutableStateOf<Exercicio?>(null) }
    val onBack = { currentExercicio.value = null }

    when (currentExercicio.value) {
        null -> Selector(onChange = { currentExercicio.value = it })
        Exercicio.POUPANCA -> Poupanca(onBack)
        Exercicio.TRANSITO -> Transito(onBack)
        Exercicio.PIZZA -> Pizza(onBack)
        Exercicio.RESTAURANTE -> Restaurante(onBack)
    }
}

@Composable
fun Selector(onChange: (Exercicio) -> Unit = {}) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                BotaoTela("Poupança") { onChange(Exercicio.POUPANCA) }
                BotaoTela("Trânsito") { onChange(Exercicio.TRANSITO) }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                BotaoTela("Pizza") { onChange(Exercicio.PIZZA) }
                BotaoTela("Restaurante") { onChange(Exercicio.RESTAURANTE) }
            }
        }
    }
}

@Composable
fun RowScope.BotaoTela(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
    ) {
        Text(texto, fontSize = 16.sp)
    }
}

enum class Exercicio {
    POUPANCA,
    TRANSITO,
    PIZZA,
    RESTAURANTE
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Atividade1Theme {
        App()
    }
}