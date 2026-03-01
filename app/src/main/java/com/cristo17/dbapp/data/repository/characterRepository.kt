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
            apiCharacters.none { it.name.lowercase() == extra.name.lowercase() }
        }

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
            CharacterItem(1014, "Moro", "10.000.000.000.000", "Mago", "${prefix}c/c0/Moro_Art.png${suffix}"),
            CharacterItem(1015, "Granolah", "15.000.000.000.000", "Cerealense", "${prefix}e/e0/Granolah_Art.png${suffix}"),
            CharacterItem(1016, "Toppo", "3.500.000.000", "Hakaishin", "${prefix}3/3e/Toppo_DBS_Art.png${suffix}"),
            CharacterItem(1017, "Dyspo", "2.800.000.000", "Raza de Dyspo", "${prefix}d/da/Dyspo_DBS_Art.png${suffix}"),
            CharacterItem(1018, "Kefla", "4.500.000.000", "Saiya-jin", "${prefix}a/ab/Kefla_SSJ2_Art.png${suffix}"),
            CharacterItem(1019, "Goku Black Rose", "3.800.000.000", "Saiya-jin/Dios", "${prefix}d/d1/Goku_Black_Rose_Art.png${suffix}"),
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
            maxKi = formatNumber(calculateMaxKiLong(extra.ki)),
            race = extra.race,
            gender = "Masculino",
            description = getExtraDescription(extra.name),
            imageUrl = extra.imageUrl,
            affiliation = "Guerreros Legendarios",
            originPlanet = "Desconocido",
            abilities = getAbilities(extra.name)
        )
    }

    private fun getExtraDescription(name: String): String {
        return when (name.lowercase()) {
            "hatchiyack" -> "Una máquina de odio creada por el Dr. Lychee, el último de los Tsufuru, para exterminar a la raza Saiya-jin. Hatchiyack es capaz de materializarse y posee un poder que supera al de los Guerreros Z en su momento."
            "goku ssj4" -> "La transformación definitiva de Goku en Dragon Ball GT. Al dominar el estado del Ozaru Dorado, Goku logra esta forma que combina la ferocidad de la bestia con el razonamiento humano."
            "vegeta ssj4" -> "Vegeta alcanza este estado gracias a la máquina de rayos Blutz de Bulma. Al igual que Goku, representa la unión perfecta del poder Saiya-jin primitivo y la conciencia guerrera."
            "gogeta ssj4" -> "La fusión más poderosa de Dragon Ball GT. Gogeta nace de la unión de Goku y Vegeta en SSJ4. Su poder es tan inmenso que su simple presencia hace temblar las dimensiones, aunque su tiempo es muy limitado."
            "janemba" -> "Un demonio de pura maldad pura nacido de la energía negativa acumulada de los villanos del infierno. En su forma final, es capaz de manipular la realidad y el espacio-tiempo."
            "cooler" -> "El hermano mayor de Freezer. Al contrario que su hermano, Cooler entrenó su cuerpo hasta alcanzar una quinta transformación que supera el poder base de un Super Saiya-jin."
            "gohan beast" -> "La forma despertada de Gohan durante la batalla contra Cell Max. Este estado representa la evolución final del potencial oculto de Gohan, manifestando un Ki salvaje y devastador."
            "gogeta blue" -> "La fusión mediante la danza de Goku y Vegeta bajo el poder del Super Saiya-jin Blue. Gogeta aparece para enfrentar a Broly, demostrando un dominio total de la batalla y una velocidad inalcanzable."
            "moro" -> "Un antiguo mago que absorbe la energía vital de planetas enteros. Después de escapar de la prisión galáctica, recuperó su juventud y puso en jaque a todo el universo con su magia devoradora."
            "granolah" -> "El último superviviente de la raza Cerealense. Granolah utilizó las Esferas del Dragón de su planeta para convertirse en el guerrero más fuerte del universo a cambio de su propia vida."
            "orange piccolo" -> "La transformación que Piccolo obtuvo al pedirle a Shenron que liberara su potencial oculto. Shenron le dio un 'regalo extra' permitiéndole alcanzar esta forma gigante y poderosa."
            "broly (lssj)" -> "El Super Saiya-jin Legendario cuya furia no tiene límites. Broly nació con un poder de pelea de 10.000 y su Ki aumenta constantemente mientras lucha, convirtiéndolo en una máquina de destrucción."
            "omega shenron" -> "El más poderoso de los Dragones Malignos, nacido de la energía negativa de los deseos egoístas. Tras absorber las 7 esferas, Omega Shenron posee el poder de todos los elementos."
            "goku black rose" -> "La versión del Super Saiya-jin Blue alcanzada por Zamasu en el cuerpo de Goku. El color rosado representa el Ki de un dios imbuido en un cuerpo Saiya-jin."
            "zamasu fusion" -> "La unión eterna de Zamasu y Goku Black mediante los pendientes Pothala. Su poder es infinito y su cuerpo es inmortal, presentándose como el 'Dios Absoluto' que purificaría el universo."
            "toppo" -> "El líder de las Tropas del Orgullo del Universo 11 y candidato a Dios de la Destrucción. En el Torneo del Poder, Toppo decide abandonar sus ideales para usar el poder del Hakai."
            "uub" -> "La reencarnación humana de Majin Buu. Entrenado por Goku, Uub posee un potencial infinito y fue capaz de fusionarse con Majin Buu gordo para convertirse en Majuub."
            "bardock ssj" -> "El padre de Goku que, tras sobrevivir a la explosión del planeta Vegeta y viajar al pasado, alcanzó la forma de Super Saiya-jin para derrotar a Chilled, iniciando la leyenda."
            "raditz" -> "El hermano mayor de Goku y el primer guerrero Saiya-jin en llegar a la Tierra en la era Z. Su llegada reveló el origen extraterrestre de Kakarotto."
            "nappa" -> "El compañero de Vegeta y un guerrero Saiya-jin de clase alta. Es conocido por su fuerza bruta y por causar estragos entre los Guerreros Z en su llegada a la Tierra."
            "cell max" -> "Una versión mejorada y gigantesca de Cell creada por el Dr. Hedo. Aunque carece de la inteligencia del original, su fuerza es tan masiva que requirió el despertar de Gohan Beast para ser derrotado."
            "vegeta ultra ego" -> "La técnica definitiva de Vegeta aprendida de Beerus. A diferencia del Ultra Instinto, el Ultra Ego se fortalece a medida que el usuario recibe daño y se sumerge en el instinto de lucha."
            "goku mui" -> "El estado completo del Ultra Instinto Dominado. Goku alcanza esta forma donde su cuerpo reacciona de manera autónoma ante cualquier peligro, logrando una defensa y ataque perfectos."
            else -> "Guerrero legendario extraído de los archivos de la Wiki. Posee habilidades únicas y un nivel de poder extraordinario que ha sido clave en las batallas más importantes del universo Dragon Ball."
        }
    }

    private fun calculateMaxKiLong(ki: String): Long {
        return try {
            val base = ki.replace(".", "").toLong()
            (base * 1.5).toLong()
        } catch (e: Exception) {
            1000000000L
        }
    }

    private fun formatNumber(number: Long): String {
        return String.format(Locale.GERMANY, "%,d", number)
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
            n.contains("shenron") -> listOf("Bola de Energía Negativa", "Rayo Ígneo")
            n.contains("piccolo") -> listOf("Makankosappo", "Regeneración", "Granada Infernal")
            n.contains("gohan") -> listOf("Masenko", "Kamehameha Especial")
            else -> listOf("Vuelo", "Ráfaga de Ki", "Sentido del Ki")
        }
    }
}