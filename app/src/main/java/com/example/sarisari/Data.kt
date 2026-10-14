package com.example.sarisari

data class StoreItem(val id: Int, val name: String, val price: Double)

private val baseNames = listOf(
    "Sinigang Mix", "Instant Noodles", "Coffee Sachet", "Egg", "Rice 1kg",
    "Soy Sauce", "Vinegar", "Sugar", "Canned Sardines", "Biscuit",
    "Candy", "Soap", "Shampoo Sachet", "Cooking Oil", "Salt"
)

val bigStore: List<StoreItem> = List(500) { i ->
    StoreItem(i + 1, "${baseNames[i % baseNames.size]} #${i + 1}", 5.0 + (i % 20) * 2.5)
}