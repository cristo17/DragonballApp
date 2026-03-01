package com.cristo17.dbapp.ui.screens.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.cristo17.dbapp.domain.model.CharacterDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBack: () -> Unit,
    viewModel: CharacterDetailViewModel = viewModel()
) {
    androidx.compose.runtime.LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        viewModel.character?.name?.uppercase() ?: "CARGANDO...",
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF57C00),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFF57C00))
            }
        } else {
            viewModel.character?.let { character ->
                DetailContent(character, padding)
            }
        }
    }
}

@Composable
fun DetailContent(character: CharacterDetail, padding: PaddingValues) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState)
            .background(Color(0xFFF8F8F8))
    ) {
        // Cabecera con Imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF57C00), Color(0xFFF5F5F5))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentScale = ContentScale.Fit,
                loading = { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = Color(0xFFF57C00)) } },
                error = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                        Spacer(Modifier.height(8.dp))
                        Text("IMAGEN NO DISPONIBLE", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
                    }
                }
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            
            // Sección de Datos Principales
            Text(
                "PERFIL DEL GUERRERO",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF57C00),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(8.dp))
            
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoItem(Icons.Default.Person, "Raza", character.race)
                    InfoItem(Icons.Default.Face, "Género", character.gender)
                    InfoItem(Icons.Default.Star, "Afiliación", character.affiliation ?: "Ninguna")
                    InfoItem(Icons.Default.Home, "Origen", character.originPlanet ?: "Desconocido")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nueva Sección de Habilidades simplificada
            if (character.abilities.isNotEmpty()) {
                Text(
                    "TÉCNICAS ESPECIALES",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF57C00),
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    character.abilities.forEach { ability ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text(ability) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Sección de Poder (Ki) con diseño resaltado
            Text(
                "NIVEL DE ENERGÍA (KI)",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF57C00),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PowerCard(
                    modifier = Modifier.weight(1f),
                    label = "Base",
                    value = character.ki,
                    icon = Icons.Default.Favorite,
                    color = Color(0xFF4CAF50)
                )
                PowerCard(
                    modifier = Modifier.weight(1f),
                    label = "Máximo",
                    value = character.maxKi,
                    icon = Icons.Default.Info,
                    color = Color(0xFFE91E63)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección Biografía
            Text(
                "HISTORIA Y BIOGRAFÍA",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF57C00),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(8.dp))
            
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFEEEEEE),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = character.description,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp,
                    color = Color(0xFF333333)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFF57C00),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$label:",
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun PowerCard(modifier: Modifier, label: String, value: String, icon: ImageVector, color: Color) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(
                value,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1
            )
        }
    }
}
