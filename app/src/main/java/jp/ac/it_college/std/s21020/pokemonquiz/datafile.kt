package jp.ac.it_college.std.s21020.pokemonquiz

data class datafile(
    val id: Int,
    val name: String,
    val entries: List<A>,
)
data class A(
    val entry_number: Int,
    val pokemon_id: Int,
)

data class datafileRoot(
    val pokedex: List<datafile>
)
