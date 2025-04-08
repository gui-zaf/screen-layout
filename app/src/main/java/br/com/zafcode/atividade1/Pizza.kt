package br.com.zafcode.atividade1

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val imagens = arrayOf(
    R.drawable.calabresa,
    R.drawable.marguerita,
    R.drawable.champignon,
    R.drawable.pepperoni
)
private val nomes = arrayOf("Calabresa", "Marguerita", "Champignon", "Pepperoni")
private val valores = arrayOf(40, 38, 45, 50)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pizza(onBack: () -> Unit) {
    var pedindo by remember { mutableStateOf(true) }
    var pizzaValorTotal by remember { mutableStateOf(0) }
    var qtdPizza by remember { mutableStateOf(intArrayOf(0, 0, 0, 0)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pizzaria da Pizza") },
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
        if (!pedindo) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.verify),
                    contentDescription = "Finalizado",
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(1f)
                )
                Text(
                    "Pedido Finalizado",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Valor Total: R$ $pizzaValorTotal",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        pedindo = true
                        pizzaValorTotal = 0
                        qtdPizza = intArrayOf(0, 0, 0, 0)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Fazer Novo Pedido")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Sabores",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                repeat(2) { linha ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        repeat(2) { coluna ->
                            val index = linha * 2 + coluna
                            PizzaItem(
                                imagem = imagens[index],
                                nome = nomes[index],
                                valor = valores[index],
                                qtd = qtdPizza[index]
                            ) {
                                pizzaValorTotal += valores[index]

                                val newQtdPizza = qtdPizza.copyOf()
                                newQtdPizza[index]++
                                qtdPizza = newQtdPizza
                            }
                        }
                    }
                }
                Column {
                    OutlinedButton(
                        onClick = {
                            pizzaValorTotal = 0
                            qtdPizza = intArrayOf(0, 0, 0, 0)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Limpar Pedido")
                    }
                    Button(
                        onClick = {
                            pedindo = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Finalizar Pedido")
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.PizzaItem(imagem: Int, nome: String, valor: Int, qtd: Int, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier.weight(1f),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(imagem),
                contentDescription = nome,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Text(nome, fontWeight = FontWeight.Bold)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("R$ $valor")
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(qtd.toString())
                }
            }
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar")
            }
        }
    }
}

@Preview
@Composable
fun PizzaPreview() {
    Pizza(onBack = {})
}
