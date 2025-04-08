package br.com.zafcode.atividade1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val reaisRegex = Regex("""\d*(\.\d{0,2})?""")
private val mesesRegex = Regex("""\d*""")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Poupanca(onBack: () -> Unit) {
    val valorInicial = remember { mutableStateOf("") }
    val aplicacaoMensal = remember { mutableStateOf("") }
    val tempoAplicacao = remember { mutableStateOf("") }
    val taxa = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Poupança") },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.piggy_bank),
                contentDescription = "Poupança",
                modifier = Modifier
                    .width(128.dp)
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = valorInicial.value,
                onValueChange = {
                    if (it.matches(reaisRegex) && it.length <= 10) valorInicial.value = it
                },
                label = { Text("Valor Inicial") },
                visualTransformation =
                if (valorInicial.value.isEmpty()) PlaceholderTransformation("Valor em R$. Ex.: 1000.00")
                else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = aplicacaoMensal.value,
                onValueChange = {
                    if (it.matches(reaisRegex) && it.length <= 10) aplicacaoMensal.value = it
                },
                label = { Text("Aplicação Mensal") },
                visualTransformation =
                if (aplicacaoMensal.value.isEmpty()) PlaceholderTransformation("Valor em R\$. Ex.: 100.00")
                else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tempoAplicacao.value,
                onValueChange = {
                    if (it.matches(mesesRegex) && it.length <= 3) tempoAplicacao.value = it
                },
                label = { Text("Tempo da Aplicação") },
                visualTransformation =
                if (tempoAplicacao.value.isEmpty()) PlaceholderTransformation("Em meses. Ex.: 6")
                else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = taxa.value,
                onValueChange = { if (it.matches(reaisRegex) && it.length <= 5) taxa.value = it },
                label = { Text("Taxa") },
                visualTransformation =
                if (taxa.value.isEmpty()) PlaceholderTransformation("Percentual. Ex.: 0.02")
                else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }
        }
    }
}

@Preview
@Composable
fun PoupancaPreview() {
    Poupanca(onBack = {})
}
