package dev.kuromiichi.retrofitapi.models

class Dogs : ArrayList<Dogs.Dog>(){
    data class Dog(
        val breeds: List<Breed>,
        val height: Int,
        val id: String,
        val url: String,
        val width: Int
    ) {
        data class Breed(
            val bred_for: String,
            val breed_group: String,
            val height: Height,
            val id: Int,
            val life_span: String,
            val name: String,
            val reference_image_id: String,
            val temperament: String,
            val weight: Weight
        ) {
            data class Height(
                val imperial: String,
                val metric: String
            )
    
            data class Weight(
                val imperial: String,
                val metric: String
            )
        }
    }
}