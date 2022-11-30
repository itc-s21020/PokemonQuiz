package jp.ac.it_college.std.s21020.pokemonquiz

data class Generation(
    val id: Int,
    val name: String,
    val entries: List<Entry>,
)
data class Entry(
    val entry_number: Int,
    val pokemon_id: Int,
)

data class PokedexJson(
    val pokedex: List<Generation>
)


data class Pokemon(
    val id: Int,
    val name: String,
)
data class PokemonJson(
    val pokemon: List<Pokemon>
)