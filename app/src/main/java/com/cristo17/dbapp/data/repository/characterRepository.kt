package com.cristo17.dbapp.data.repository

import com.cristo17.dbapp.data.remote.RetrofitClient
import com.cristo17.dbapp.domain.model.CharacterDetail
import com.cristo17.dbapp.domain.model.CharacterItem
import java.util.Locale

class CharacterRepository {
    private val api = RetrofitClient.instance

    suspend fun getCharacters(): List<CharacterItem> {
        val response = api.getCharacters(limit = 60)
        val apiCharacters = response.items.map { dto ->
            CharacterItem(
                id = dto.id,
                name = dto.name,
                ki = dto.ki,
                race = translateRace(dto.race),
                imageUrl = dto.image
            )
        }

        val extraCharacters = getExtraCharacters().filter { extra ->
            apiCharacters.none { it.name.lowercase().contains(extra.name.lowercase()) }
        }

        // Combinamos y aseguramos los 100 exactos
        return (apiCharacters + extraCharacters).sortedBy { it.id }.take(100)
    }

    suspend fun getCharacterDetail(id: Int): CharacterDetail {
        if (id >= 1000) return getExtraCharacterDetail(id)

        val dto = api.getCharacterDetail(id)
        return CharacterDetail(
            id = dto.id,
            name = dto.name,
            ki = dto.ki,
            maxKi = dto.maxKi,
            race = translateRace(dto.race),
            gender = translateGender(dto.gender),
            description = dto.description,
            imageUrl = dto.image,
            affiliation = dto.affiliation,
            originPlanet = dto.originPlanet?.name,
            abilities = getAbilities(dto.name)
        )
    }

    private fun getExtraCharacters(): List<CharacterItem> {
        val prefix = "https://static.wikia.nocookie.net/dragonball/images/"
        val suffix = "/revision/latest?cb=20180211155315&path-prefix=es"
        
        return listOf(
            CharacterItem(1001, "Hatchiyack", "2.000.000.000", "Androide", "${prefix}4/42/Hatchiyack_Art.png${suffix}"),
            CharacterItem(1002, "Goku SSJ4", "1.500.000.000", "Saiya-jin", "${prefix}d/de/Goku_SSJ4_Art.png${suffix}"),
            CharacterItem(1003, "Vegeta SSJ4", "1.450.000.000", "Saiya-jin", "${prefix}3/33/Vegeta_SSJ4_GT.png${suffix}"),
            CharacterItem(1004, "Gogeta SSJ4", "5.000.000.000", "Saiya-jin", "${prefix}d/df/Gogeta_SSJ4_GT_Art.png${suffix}"),
            CharacterItem(1005, "Janemba", "2.500.000.000", "Demonio", "${prefix}a/a2/Super_Janemba_Art.png${suffix}"),
            CharacterItem(1006, "Cooler", "1.800.000.000", "Raza de Freezer", "${prefix}4/42/Cooler_Final_Form_Art.png${suffix}"),
            CharacterItem(1007, "Tapion", "500.000", "Konatsiano", "${prefix}a/a3/Tapion_Art.png${suffix}"),
            CharacterItem(1008, "Bojack", "1.500.000.000", "Hera-jin", "${prefix}c/c2/Bojack_Full_Power_Art.png${suffix}"),
            CharacterItem(1009, "Super 17", "3.000.000.000", "Androide", "${prefix}5/5b/Super_17_GT_Art.png${suffix}"),
            CharacterItem(1010, "Omega Shenron", "4.000.000.000", "Dragón Maligno", "${prefix}0/0b/Omega_Shenron_GT_Art.png${suffix}"),
            CharacterItem(1011, "Baby Vegeta", "1.200.000.000", "Tsuru-jin", "${prefix}c/c1/Baby_Vegeta_GT_Art.png${suffix}"),
            CharacterItem(1012, "Bardock SSJ", "1.000.000", "Saiya-jin", "${prefix}3/3a/Bardock_SSJ_Art.png${suffix}"),
            CharacterItem(1013, "Turles", "30.000", "Saiya-jin", "${prefix}9/93/Turles_Art.png${suffix}"),
            CharacterItem(1014, "Moro", "10.000.000.000", "Mago", "${prefix}c/c0/Moro_Art.png${suffix}"),
            CharacterItem(1015, "Granolah", "15.000.000.000", "Cerealense", "${prefix}e/e0/Granolah_Art.png${suffix}"),
            CharacterItem(1016, "Toppo", "3.500.000.000", "Hakaishin", "${prefix}3/3e/Toppo_DBS_Art.png${suffix}"),
            CharacterItem(1017, "Dyspo", "2.800.000.000", "Raza de Dyspo", "${prefix}d/da/Dyspo_DBS_Art.png${suffix}"),
            CharacterItem(1018, "Kefla", "4.500.000.000", "Saiya-jin", "${prefix}a/ab/Kefla_SSJ2_Art.png${suffix}"),
            CharacterItem(1019, "Goku Black", "3.800.000.000", "Saiya-jin/Dios", "${prefix}d/d1/Goku_Black_Rose_Art.png${suffix}"),
            CharacterItem(1020, "Metal Cooler", "2.000.000.000", "Raza de Freezer", "${prefix}4/4d/Metal_Cooler_Art.png${suffix}"),
            CharacterItem(1021, "Broly (LSSJ)", "2.500.000.000", "Saiya-jin Legendario", "${prefix}d/da/Broly_LSSJ_Art.png${suffix}"),
            CharacterItem(1022, "Hirudegarn", "3.000.000.000", "Monstruo", "${prefix}4/44/Hirudegarn_Art.png${suffix}"),
            CharacterItem(1023, "Uub", "1.500.000.000", "Humano/Majin", "${prefix}d/de/Majuub_GT_Art.png${suffix}"),
            CharacterItem(1024, "Nova Shenron", "2.800.000.000", "Dragón Maligno", "${prefix}3/33/Nuova_Shenron_Art.png${suffix}"),
            CharacterItem(1025, "Eis Shenron", "2.700.000.000", "Dragón Maligno", "${prefix}f/f3/Eis_Shenron_Art.png${suffix}"),
            CharacterItem(1026, "Android 13", "1.200.000.000", "Androide", "${prefix}3/3e/Android_13_Art.png${suffix}"),
            CharacterItem(1027, "Slug", "150.000.000", "Namekuseijin", "${prefix}5/5e/Lord_Slug_Art.png${suffix}"),
            CharacterItem(1028, "Garlic Jr.", "1.000.000", "Demonio", "${prefix}a/a1/Garlic_Jr_Full_Power_Art.png${suffix}"),
            CharacterItem(1029, "Goku Jr.", "1.000.000", "Saiya-jin", "${prefix}4/42/Goku_Jr_Art.png${suffix}"),
            CharacterItem(1030, "Vegeta Jr.", "1.000.000", "Saiya-jin", "${prefix}d/d1/Vegeta_Jr_Art.png${suffix}"),
            CharacterItem(1031, "Cabba", "1.200.000.000", "Saiya-jin", "${prefix}d/d3/Cabba_SSJ_Art.png${suffix}"),
            CharacterItem(1032, "Caulifla", "1.300.000.000", "Saiya-jin", "${prefix}e/e0/Caulifla_SSJ2_Art.png${suffix}"),
            CharacterItem(1033, "Kale", "2.000.000.000", "Saiya-jin", "${prefix}0/05/Kale_SSJ_Berserk_Art.png${suffix}"),
            CharacterItem(1034, "Zamasu Fusion", "4.500.000.000", "Dios", "${prefix}a/a2/Zamasu_Fusion_Art.png${suffix}"),
            CharacterItem(1035, "Ribrianne", "1.400.000.000", "Humanoide", "${prefix}3/3e/Ribrianne_Art.png${suffix}"),
            CharacterItem(1036, "Bergamo", "1.500.000.000", "Raza Lobo", "${prefix}4/4a/Bergamo_Art.png${suffix}"),
            CharacterItem(1037, "Aniraza", "4.000.000.000", "Robot", "${prefix}3/3e/Aniraza_Art.png${suffix}"),
            CharacterItem(1038, "Frost", "1.100.000.000", "Raza de Freezer", "${prefix}d/d4/Frost_Final_Form_Art.png${suffix}"),
            CharacterItem(1039, "Champa", "50.000.000.000", "Dios", "${prefix}3/3e/Champa_Art.png${suffix}"),
            CharacterItem(1040, "Vados", "100.000.000.000", "Ángel", "${prefix}3/3e/Vados_Art.png${suffix}"),
            CharacterItem(1041, "Whis", "120.000.000.000", "Ángel", "${prefix}3/3e/Whis_Art.png${suffix}"),
            CharacterItem(1042, "Beerus", "80.000.000.000", "Dios", "${prefix}3/3e/Beerus_Art.png${suffix}"),
            CharacterItem(1043, "Raditz", "1.500", "Saiya-jin", "${prefix}1/1a/Raditz_DBZ_Art.png${suffix}"),
            CharacterItem(1044, "Nappa", "4.000", "Saiya-jin", "${prefix}e/e9/Nappa_DBZ_Art.png${suffix}"),
            CharacterItem(1045, "Zarbon", "23.000", "Raza de Zarbon", "${prefix}a/a3/Zarbon_Art.png${suffix}"),
            CharacterItem(1046, "Dodoria", "22.000", "Raza de Dodoria", "${prefix}4/4a/Dodoria_Art.png${suffix}"),
            CharacterItem(1047, "Gogeta Blue", "40.000.000.000", "Saiya-jin", "${prefix}5/5a/Gogeta_Blue_Art.png${suffix}"),
            CharacterItem(1048, "Gohan Beast", "30.000.000.000", "Saiya-jin", "${prefix}2/2b/Gohan_Beast_Art.png${suffix}"),
            CharacterItem(1049, "Orange Piccolo", "12.000.000.000", "Namekuseijin", "${prefix}4/4a/Orange_Piccolo_Art.png${suffix}"),
            CharacterItem(1050, "Cell Max", "20.000.000.000", "Androide", "${prefix}4/4a/Cell_Max_Art.png${suffix}")
        )
    }

    private fun getExtraCharacterDetail(id: Int): CharacterDetail {
        val extra = getExtraCharacters().find { it.id == id } ?: getExtraCharacters().first()
        return CharacterDetail(
            id = extra.id,
            name = extra.name,
            ki = extra.ki,
            maxKi = String.format(Locale.GERMANY, "%,d", (extra.ki.replace(".", "").toLongOrNull() ?: 0) * 2),
            race = extra.race,
            gender = "Masculino",
            description = "Guerrero legendario extraído de los archivos de Cristo17.",
            imageUrl = extra.imageUrl,
            affiliation = "Guerreros Legendarios",
            originPlanet = "Desconocido",
            abilities = getAbilities(extra.name)
        )
    }

    private fun translateRace(race: String): String {
        return when (race.lowercase()) {
            "saiyan" -> "Saiya-jin"
            "human" -> "Humano"
            "namekian" -> "Namekuseijin"
            "frieza race" -> "Raza de Freezer"
            "android" -> "Androide"
            "majin" -> "Majin"
            "god" -> "Dios"
            "angel" -> "Ángel"
            "jiren race" -> "Raza de Jiren"
            "unknown" -> "Desconocido"
            else -> race
        }
    }

    private fun translateGender(gender: String): String {
        return when (gender.lowercase()) {
            "male" -> "Masculino"
            "female" -> "Femenino"
            "unknown" -> "Desconocido"
            else -> gender
        }
    }

    private fun getAbilities(name: String): List<String> {
        val n = name.lowercase()
        return when {
            n.contains("goku") -> listOf("Kamehameha", "Genkidama", "Kaioken", "Teletransportación")
            n.contains("vegeta") -> listOf("Galick Gun", "Final Flash", "Big Bang Attack")
            n.contains("hatchiyack") -> listOf("Cañón Revancha", "Multiplicación", "Energía de Odio")
            n.contains("janemba") -> listOf("Espada Dimensional", "Cañón de Ilusión")
            n.contains("cooler") -> listOf("Supernova", "Rayo de la Muerte")
            n.contains("broly") -> listOf("Gigantic Meteor", "Eraser Cannon")
            n.contains("gogeta") -> listOf("Rompedor de Polvo Estelar", "Big Bang Kamehameha")
            else -> listOf("Vuelo", "Ráfaga de Ki", "Sentido del Ki")
        }
    }
}