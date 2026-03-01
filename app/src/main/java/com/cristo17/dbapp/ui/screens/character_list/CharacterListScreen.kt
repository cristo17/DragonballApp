package com.cristo17.dbapp.ui.screens.character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.cristo17.dbapp.domain.model.CharacterItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharacterListViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "DRAGON BALL CODEX", 
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        if (!viewModel.isLoading && viewModel.characters.isNotEmpty()) {
                            Text(
                                text = "${viewModel.characters.size} GUERREROS REGISTRADOS",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.8f),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF57C00)
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF1F1F1))) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFF57C00))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewModel.characters) { character ->
                        CharacterRow(character = character, onClick = { onCharacterClick(character.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterRow(character: CharacterItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, 
            modifier = Modifier.padding(12.dp)
        ) {
            SubcomposeAsyncImage(
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier.size(70.dp),
                loading = { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = Color(0xFFF57C00), modifier = Modifier.size(20.dp)) } },
                error = { 
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize().background(Color(0xFFEEEEEE))
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                        Text("S/I", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    }
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = character.name.uppercase(), 
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Text(
                    text = "Raza: ${character.race}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
