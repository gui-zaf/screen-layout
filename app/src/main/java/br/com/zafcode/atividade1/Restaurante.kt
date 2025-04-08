package br.com.zafcode.atividade1

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.zafcode.atividade1.ui.theme.Typography

private val reaisRegex = Regex("""\d*(\.\d{0,2})?""")
private val pessoasRegex = Regex("""\d*""")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Restaurante(onBack: () -> Unit) {
    var consumoTotal by remember { mutableStateOf("") }
    var couvert by remember { mutableStateOf("") }
    var dividirConta by remember { mutableStateOf("") }
    var taxaServico by remember { mutableStateOf("") }
    var contaTotal by remember { mutableStateOf("") }
    var valorPorPessoa by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(
            primary = Color(0xFFFD8400),
            secondaryContainer = Color(0xFFFFC17B),
        ),
        typography = Typography,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Restaurante") },
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.restaurant),
                    contentDescription = "Restaurante",
                    modifier = Modifier
                        .width(128.dp)
                        .align(Alignment.CenterHorizontally)
                )
                OutlinedTextField(
                    value = consumoTotal,
                    onValueChange = {
                        if (it.matches(reaisRegex) && it.length <= 10) consumoTotal = it
                    },
                    label = { Text("Consumo Total") },
                    visualTransformation =
                    if (consumoTotal.isEmpty()) PlaceholderTransformation("Valor em R$. Ex.: 240.00")
                    else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = couvert,
                    onValueChange = {
                        if (it.matches(reaisRegex) && it.length <= 10) couvert = it
                    },
                    label = { Text("Couvert Artístico") },
                    visualTransformation =
                    if (couvert.isEmpty()) PlaceholderTransformation("Valor em R\$. Ex.: 10.00")
                    else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dividirConta,
                    onValueChange = {
                        if (it.matches(pessoasRegex) && it.length <= 3) dividirConta = it
                    },
                    label = { Text("Dividir Conta") },
                    visualTransformation =
                    if (dividirConta.isEmpty()) PlaceholderTransformation("Quantidade de pessoas. Ex.: 5")
                    else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    shape = MaterialTheme.shapes.extraSmall,
                    onClick = {
                        val consumo = consumoTotal.toFloat()
                        val couvertArt = couvert.toFloat()
                        val pessoas = dividirConta.toInt()
                        val taxa = (consumo + couvertArt) * 0.1f
                        val total = consumo + couvertArt + taxa
                        val porPessoa = total / pessoas
                        taxaServico = "%.2f".format(taxa)
                        contaTotal = "%.2f".format(total)
                        valorPorPessoa = "%.2f".format(porPessoa)

                        keyboardController?.hide()
                    },
                    enabled = consumoTotal.isNotEmpty() && couvert.isNotEmpty() && dividirConta.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Calcular")
                }
                OutlinedTextField(
                    value = taxaServico,
                    onValueChange = {},
                    label = { Text("Taxa de Serviço") },
                    visualTransformation =
                    if (taxaServico.isEmpty()) PlaceholderTransformation("Percentual. Ex.: 10%")
                    else VisualTransformation.None,
                    readOnly = true,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = contaTotal,
                    onValueChange = {},
                    label = { Text("Conta Total") },
                    visualTransformation =
                    if (contaTotal.isEmpty()) PlaceholderTransformation("Valor em R$. Ex.: 275.00")
                    else VisualTransformation.None,
                    readOnly = true,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = valorPorPessoa,
                    onValueChange = {},
                    label = { Text("Valor por Pessoa") },
                    visualTransformation =
                    if (valorPorPessoa.isEmpty()) PlaceholderTransformation("Valor em R$. Ex.: 55.00")
                    else VisualTransformation.None,
                    readOnly = true,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    shape = MaterialTheme.shapes.extraSmall,
                    onClick = {
                        consumoTotal = ""
                        couvert = ""
                        dividirConta = ""
                        taxaServico = ""
                        contaTotal = ""
                        valorPorPessoa = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Limpar")
                }
            }
        }
    }
}

@Preview
@Composable
fun RestaurantePreview() {
    Restaurante(onBack = {})
}
