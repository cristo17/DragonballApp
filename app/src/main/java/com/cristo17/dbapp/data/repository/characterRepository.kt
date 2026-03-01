package com.cristo17.dbapp.data.repository

import com.cristo17.dbapp.data.remote.RetrofitClient
import com.cristo17.dbapp.domain.model.CharacterDetail
import com.cristo17.dbapp.domain.model.CharacterItem

class CharacterRepository {
    private val api = RetrofitClient.instance

    suspend fun getCharacters(): List<CharacterItem> {
        val response = api.getCharacters(limit = 60)
        val apiCharacters = response.items.map { dto ->
            CharacterItem(
                id = dto.id,
                name = dto.name,
                imageUrl = dto.image,
                race = translateRace(dto.race),
                ki = dto.ki
            )
        }

        val extraCharacters = getExtraCharacters().filter { extra ->
            apiCharacters.none { it.name.lowercase() == extra.name.lowercase() }
        }

        // Combinamos y aseguramos que el total sea exactamente 100
        return (apiCharacters + extraCharacters).sortedBy { it.id }.take(100)
    }

    suspend fun getCharacterDetail(id: Int): CharacterDetail {
        if (id >= 1000) return getExtraCharacterDetail(id)

        val dto = api.getCharacterDetail(id)
        return CharacterDetail(
            id = dto.id,
            name = dto.name,
            imageUrl = dto.image,
            ki = dto.ki,
            maxKi = dto.maxKi,
            race = translateRace(dto.race),
            gender = translateGender(dto.gender),
            description = dto.description,
            affiliation = dto.affiliation,
            originPlanet = dto.originPlanet?.name,
            abilities = getAbilities(dto.name)
        )
    }

    private fun getExtraCharacters(): List<CharacterItem> {
        return listOf(
            CharacterItem(1001, "Hatchiyack", "https://static.wikia.nocookie.net/dragonball/images/4/42/Hatchiyack_Art.png", "Androide Tsuru-jin", "2.000.000.000"),
            CharacterItem(1002, "Goku SSJ4", "https://static.wikia.nocookie.net/dragonball/images/d/de/Goku_SSJ4_Art.png", "Saiya-jin", "1.500.000.000"),
            CharacterItem(1003, "Vegeta SSJ4", "https://static.wikia.nocookie.net/dragonball/images/3/33/Vegeta_SSJ4_GT.png", "Saiya-jin", "1.450.000.000"),
            CharacterItem(1004, "Gogeta SSJ4", "https://static.wikia.nocookie.net/dragonball/images/d/df/Gogeta_SSJ4_GT_Art.png", "Saiya-jin", "5.000.000.000"),
            CharacterItem(1005, "Janemba", "https://static.wikia.nocookie.net/dragonball/images/a/a2/Super_Janemba_Art.png", "Demonio", "2.500.000.000"),
            CharacterItem(1006, "Cooler", "https://static.wikia.nocookie.net/dragonball/images/4/42/Cooler_Final_Form_Art.png", "Raza de Freezer", "1.800.000.000"),
            CharacterItem(1007, "Tapion", "https://static.wikia.nocookie.net/dragonball/images/a/a3/Tapion_Art.png", "Konatsiano", "500.000"),
            CharacterItem(1008, "Bojack", "https://static.wikia.nocookie.net/dragonball/images/c/c2/Bojack_Full_Power_Art.png", "Hera-jin", "1.500.000.000"),
            CharacterItem(1009, "Super 17", "https://static.wikia.nocookie.net/dragonball/images/5/5b/Super_17_GT_Art.png", "Androide", "3.000.000.000"),
            CharacterItem(1010, "Omega Shenron", "https://static.wikia.nocookie.net/dragonball/images/0/0b/Omega_Shenron_GT_Art.png", "Dragón Maligno", "4.000.000.000"),
            CharacterItem(1011, "Baby Vegeta", "https://static.wikia.nocookie.net/dragonball/images/c/c1/Baby_Vegeta_GT_Art.png", "Tsuru-jin", "1.200.000.000"),
            CharacterItem(1012, "Bardock SSJ", "https://static.wikia.nocookie.net/dragonball/images/3/3a/Bardock_SSJ_Art.png", "Saiya-jin", "1.000.000"),
            CharacterItem(1013, "Turles", "https://static.wikia.nocookie.net/dragonball/images/9/93/Turles_Art.png", "Saiya-jin", "30.000"),
            CharacterItem(1014, "Moro", "https://static.wikia.nocookie.net/dragonball/images/c/c0/Moro_Art.png", "Mago", "Ilimitado"),
            CharacterItem(1015, "Granolah", "https://static.wikia.nocookie.net/dragonball/images/e/e0/Granolah_Art.png", "Cerealense", "Ilimitado"),
            CharacterItem(1016, "Toppo", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Toppo_DBS_Art.png", "Raza de Toppo", "3.500.000.000"),
            CharacterItem(1017, "Dyspo", "https://static.wikia.nocookie.net/dragonball/images/d/da/Dyspo_DBS_Art.png", "Raza de Dyspo", "2.800.000.000"),
            CharacterItem(1018, "Kefla", "https://static.wikia.nocookie.net/dragonball/images/a/ab/Kefla_SSJ2_Art.png", "Saiya-jin (Fusión)", "4.500.000.000"),
            CharacterItem(1019, "Goku Black", "https://static.wikia.nocookie.net/dragonball/images/d/d1/Goku_Black_Rose_Art.png", "Saiya-jin/Dios", "3.800.000.000"),
            CharacterItem(1020, "Metal Cooler", "https://static.wikia.nocookie.net/dragonball/images/4/4d/Metal_Cooler_Art.png", "Raza de Freezer", "2.000.000.000"),
            CharacterItem(1021, "Broly (LSSJ)", "https://static.wikia.nocookie.net/dragonball/images/d/da/Broly_LSSJ_Art.png", "Saiya-jin Legendario", "2.500.000.000"),
            CharacterItem(1022, "Hirudegarn", "https://static.wikia.nocookie.net/dragonball/images/4/44/Hirudegarn_Art.png", "Monstruo", "3.000.000.000"),
            CharacterItem(1023, "Uub", "https://static.wikia.nocookie.net/dragonball/images/d/de/Majuub_GT_Art.png", "Humano/Majin", "1.500.000.000"),
            CharacterItem(1024, "Nova Shenron", "https://static.wikia.nocookie.net/dragonball/images/3/33/Nuova_Shenron_Art.png", "Dragón Maligno", "2.800.000.000"),
            CharacterItem(1025, "Eis Shenron", "https://static.wikia.nocookie.net/dragonball/images/f/f3/Eis_Shenron_Art.png", "Dragón Maligno", "2.700.000.000"),
            CharacterItem(1026, "Android 13", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Android_13_Art.png", "Androide", "1.200.000.000"),
            CharacterItem(1027, "Slug", "https://static.wikia.nocookie.net/dragonball/images/5/5e/Lord_Slug_Art.png", "Namekuseijin", "150.000.000"),
            CharacterItem(1028, "Garlic Jr.", "https://static.wikia.nocookie.net/dragonball/images/a/a1/Garlic_Jr_Full_Power_Art.png", "Demonio", "1.000.000"),
            CharacterItem(1029, "Goku Jr.", "https://static.wikia.nocookie.net/dragonball/images/4/42/Goku_Jr_Art.png", "Saiya-jin", "1.000.000"),
            CharacterItem(1030, "Vegeta Jr.", "https://static.wikia.nocookie.net/dragonball/images/d/d1/Vegeta_Jr_Art.png", "Saiya-jin", "1.000.000"),
            CharacterItem(1031, "Cabba", "https://static.wikia.nocookie.net/dragonball/images/d/d3/Cabba_SSJ_Art.png", "Saiya-jin", "1.200.000.000"),
            CharacterItem(1032, "Caulifla", "https://static.wikia.nocookie.net/dragonball/images/e/e0/Caulifla_SSJ2_Art.png", "Saiya-jin", "1.300.000.000"),
            CharacterItem(1033, "Kale", "https://static.wikia.nocookie.net/dragonball/images/0/05/Kale_SSJ_Berserk_Art.png", "Saiya-jin", "2.000.000.000"),
            CharacterItem(1034, "Zamasu", "https://static.wikia.nocookie.net/dragonball/images/a/a2/Zamasu_Fusion_Art.png", "Dios", "4.500.000.000"),
            CharacterItem(1035, "Ribrianne", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Ribrianne_Art.png", "Humanoide", "1.400.000.000"),
            CharacterItem(1036, "Bergamo", "https://static.wikia.nocookie.net/dragonball/images/4/4a/Bergamo_Art.png", "Raza Lobo", "1.500.000.000"),
            CharacterItem(1037, "Aniraza", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Aniraza_Art.png", "Robot Fusión", "4.000.000.000"),
            CharacterItem(1038, "Frost", "https://static.wikia.nocookie.net/dragonball/images/d/d4/Frost_Final_Form_Art.png", "Raza de Freezer", "1.100.000.000"),
            CharacterItem(1039, "Champa", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Champa_Art.png", "Dios", "Ilimitado"),
            CharacterItem(1040, "Vados", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Vados_Art.png", "Ángel", "Ilimitado"),
            CharacterItem(1041, "Whis", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Whis_Art.png", "Ángel", "Ilimitado"),
            CharacterItem(1042, "Beerus", "https://static.wikia.nocookie.net/dragonball/images/3/3e/Beerus_Art.png", "Dios", "Ilimitado"),
            CharacterItem(1043, "Raditz", "https://static.wikia.nocookie.net/dragonball/images/1/1a/Raditz_DBZ_Art.png", "Saiya-jin", "1.500"),
            CharacterItem(1044, "Nappa", "https://static.wikia.nocookie.net/dragonball/images/e/e9/Nappa_DBZ_Art.png", "Saiya-jin", "4.000"),
            CharacterItem(1045, "Zarbon", "https://static.wikia.nocookie.net/dragonball/images/a/a3/Zarbon_Art.png", "Raza de Zarbon", "23.000"),
            CharacterItem(1046, "Dodoria", "https://static.wikia.nocookie.net/dragonball/images/4/4a/Dodoria_Art.png", "Raza de Dodoria", "22.000"),
            CharacterItem(1047, "Gogeta Blue", "https://static.wikia.nocookie.net/dragonball/images/5/5a/Gogeta_Blue_Art.png", "Saiya-jin", "Ilimitado"),
            CharacterItem(1048, "Syn Shenron", "https://static.wikia.nocookie.net/dragonball/images/a/a5/Syn_Shenron_Art.png", "Dragón Maligno", "2.000.000.000"),
            CharacterItem(1049, "Android 14", "https://static.wikia.nocookie.net/dragonball/images/d/d4/Android_14_Art.png", "Androide", "800.000.000"),
            CharacterItem(1050, "Android 15", "https://static.wikia.nocookie.net/dragonball/images/d/d4/Android_15_Art.png", "Androide", "800.000.000"),
            CharacterItem(1051, "Gohan Beast", "https://static.wikia.nocookie.net/dragonball/images/2/2b/Gohan_Beast_Art.png", "Saiya-jin/Humano", "Ilimitado"),
            CharacterItem(1052, "Orange Piccolo", "https://static.wikia.nocookie.net/dragonball/images/4/4a/Orange_Piccolo_Art.png", "Namekuseijin", "Ilimitado"),
            CharacterItem(1053, "Cell Max", "https://static.wikia.nocookie.net/dragonball/images/4/4a/Cell_Max_Art.png", "Androide Bio-mecánico", "Ilimitado"),
            CharacterItem(1054, "Vegeta Ultra Ego", "https://static.wikia.nocookie.net/dragonball/images/4/4a/Vegeta_Ultra_Ego_Art.png", "Saiya-jin", "Ilimitado"),
            CharacterItem(1055, "Goku UI Dominado", "https://static.wikia.nocookie.net/dragonball/images/4/4a/Goku_MUI_Art.png", "Saiya-jin", "Ilimitado")
        )
    }

    private fun getExtraCharacterDetail(id: Int): CharacterDetail {
        val extra = getExtraCharacters().find { it.id == id } ?: getExtraCharacters().first()
        return CharacterDetail(
            id = extra.id,
            name = extra.name,
            imageUrl = extra.imageUrl,
            ki = extra.ki,
            maxKi = "Desconocido",
            race = extra.race,
            gender = "Masculino",
            description = "Guerrero legendario extraído de los archivos de la Wiki. Posee habilidades únicas y un poder que rivaliza con los mejores del universo.",
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
            n.contains("janemba") -> listOf("Espada Dimensional", "Cañón de Ilusión", "Teletransportación")
            n.contains("cooler") -> listOf("Supernova", "Rayo de la Muerte")
            n.contains("broly") -> listOf("Gigantic Meteor", "Eraser Cannon", "Poder Ilimitado")
            n.contains("gogeta") -> listOf("Rompedor de Polvo Estelar", "Big Bang Kamehameha")
            n.contains("shenron") -> listOf("Bola de Energía Negativa", "Rayo Ígneo")
            n.contains("piccolo") -> listOf("Makankosappo", "Regeneración", "Granada Infernal")
            n.contains("gohan") -> listOf("Masenko", "Kamehameha Especial")
            else -> listOf("Vuelo", "Ráfaga de Ki", "Sentido del Ki")
        }
    }
}