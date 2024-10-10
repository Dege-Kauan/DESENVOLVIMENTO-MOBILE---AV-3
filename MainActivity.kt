package com.example.listadecompras

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaFacilApp(
                onShowMarketsClick = { openMapsForNearbyMarkets() }
            )
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Verificar permissões de localização
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    // Método para obter a localização do usuário e abrir o Google Maps
    private fun openMapsForNearbyMarkets() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val gmmIntentUri = Uri.parse(
                        "geo:$latitude,$longitude?q=supermarket&z=15" // "z=15" ajusta o zoom do mapa
                    )
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    // Verificar se o Google Maps está instalado e iniciar a atividade
                    if (mapIntent.resolveActivity(packageManager) != null) {
                        startActivity(mapIntent)
                    } else {
                        Toast.makeText(this, "Google Maps não está instalado!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Não foi possível obter a localização!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaFacilApp(onShowMarketsClick: () -> Unit) {
    var nomeProduto by remember { mutableStateOf("") }
    var valorProduto by remember { mutableStateOf("") }
    var listaCompras by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var total by remember { mutableStateOf(0.0) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Adiciona a imagem de fundo
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título SUPERMERCADO centralizado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF5722), shape = RectangleShape)
                    .padding(8.dp)
            ) {
                Text(
                    text = "LISTA FÁCIL",
                    fontSize = 32.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campos de produto e valor
            TextField(
                value = nomeProduto,
                onValueChange = { nomeProduto = it },
                label = { Text("Produto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = valorProduto,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == ',' || char == '.' }) {
                        valorProduto = it
                    }
                },
                label = { Text("Valor") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botão de adicionar item à lista
            Button(
                onClick = {
                    if (nomeProduto.isNotEmpty() && valorProduto.isNotEmpty()) {
                        listaCompras = listaCompras + (nomeProduto to valorProduto)
                        total += valorProduto.toDoubleOrNull() ?: 0.0
                        nomeProduto = ""
                        valorProduto = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Adicionar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Título da lista de compras
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Lista de Compras:",
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Exibição dos itens da lista
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(start = 36.dp) // Mover um pouco mais para a direita
            ) {
                listaCompras.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("- ${item.first}: R$${item.second},00", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))

                        Row {
                            IconButton(onClick = {
                                // Editar item
                                nomeProduto = item.first
                                valorProduto = item.second
                                listaCompras = listaCompras - item
                                total -= item.second.toDoubleOrNull() ?: 0.0
                            }) {
                                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = {
                                // Remover item
                                listaCompras = listaCompras - item
                                total -= item.second.toDoubleOrNull() ?: 0.0
                            }, modifier = Modifier.padding(end = 16.dp)) { // Ícone de remover mais para a esquerda
                                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remover")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total acumulado no rodapé com fundo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Text(
                    "Total: R$${String.format("%.2f", total)}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botão para mostrar mercados próximos
            Button(
                onClick = onShowMarketsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mostrar Mercados Próximos")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaFacilPreview() {
    ListaFacilApp(onShowMarketsClick = {})
}