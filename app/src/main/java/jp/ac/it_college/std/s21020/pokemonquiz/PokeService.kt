package jp.ac.it_college.std.s21020.pokemonquiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface PokeService {
    @GET("pokemon/{random}/")
    fun fetchPoke(@Path("random") random: Int): Call<PokemonResponce>
}