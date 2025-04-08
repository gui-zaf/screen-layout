package br.com.zafcode.atividade1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val placas = arrayOf(
    R.drawable.altura_max,
    R.drawable.cachaca,
    R.drawable.direita_nunca,
    R.drawable.direita_pode,
    R.drawable.sobe_desce
)

private val matrizOpcoes = arrayOf(
    arrayOf("Altura Máxima", "3 Minutos", "Altura Mínima", "Faixa de Pedestre"),
    arrayOf("Altura Máxima", "Velocidade Máxima", "Velocidade Mínima", "Lombada"),
    arrayOf("Proibido Virar à Direita", "Esquerda Livre", "Diretas Já", "Cruzamento"),
    arrayOf("Placa Amarela", "Curva à Direita", "Semáforo", "Homens Trabalhando"),
    arrayOf("Mão Sem Via", "Via Sem Mão", "Via de Mão Única", "Via de Mão Dupla"),
)

private val respostas = intArrayOf(0, 1, 0, 1, 3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transito(onBack: () -> Unit) {
    var perguntaIndex by remember { mutableStateOf(0) }
    var respostaIndex by remember { mutableStateOf(-1) }
    var respondendo by remember { mutableStateOf(true) }
    var acertos by remember { mutableStateOf(0) }

    val coresRespondendo = ButtonDefaults.outlinedButtonColors()
    val coresAcerto = ButtonDefaults.outlinedButtonColors(
        disabledContainerColor = Color(0xFF4CAF50),
        disabledContentColor = Color.White
    )
    val coresErro = ButtonDefaults.outlinedButtonColors(
        disabledContainerColor = Color(0xFFF44336),
        disabledContentColor = Color.White
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz Trânsito") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    ) { innerPadding ->
        if (perguntaIndex > 4)
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    when {
                        acertos == 5 -> "👑"
                        acertos >= 3 -> "🎉"
                        acertos >= 1 -> "🥺"
                        else -> "🤡"
                    }, fontSize = 64.sp
                )
                Text("Você acertou $acertos de 5 questões", fontSize = 24.sp)
                Button(
                    onClick = {
                        perguntaIndex = 0
                        respostaIndex = -1
                        respondendo = true
                        acertos = 0
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tentar Novamente")
                }
            }
        else
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Questão: ${perguntaIndex + 1}/5", fontSize = 24.sp)
                Image(
                    painter = painterResource(id = placas[perguntaIndex]),
                    contentDescription = "Placa",
                    modifier = Modifier
                        .height(200.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    "Qual o significado dessa placa?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                repeat(4) {
                    OutlinedButton(
                        onClick = {
                            if (respondendo) {
                                respostaIndex = it
                                respondendo = false
                                if (respostas[perguntaIndex] == it) acertos++
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = respondendo,
                        colors = when {
                            !respondendo && respostas[perguntaIndex] == it -> coresAcerto
                            !respondendo && respostaIndex == it && respostas[perguntaIndex] != it -> coresErro
                            else -> coresRespondendo
                        }
                    ) {
                        Text(matrizOpcoes[perguntaIndex][it])
                    }
                }

                Button(
                    onClick = {
                        perguntaIndex++
                        respostaIndex = -1
                        respondendo = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !respondendo
                ) {
                    Text(if (perguntaIndex == 4) "Ver Resultados" else "Próxima Pergunta")
                }
            }
    }
}

@Preview
@Composable
fun TransitoPreview() {
    Transito(onBack = {})
}
