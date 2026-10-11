
package com.example.minishopmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import org.json.JSONObject

data class Produit(
    val nom: String,
    val prix: Int,
    val image: String
)

class MainActivity : ComponentActivity() {

    private lateinit var listViewProducts: ListView
    private lateinit var tvCatalogTitle: TextView
    private val produits = mutableListOf<Produit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MiniShop", "MainActivity créée")

        listViewProducts = findViewById(R.id.listViewProducts)
        tvCatalogTitle = findViewById(R.id.tvCatalogTitle)

        val nomUtilisateur =
            intent.getStringExtra("USER_NAME") ?: ""

        tvCatalogTitle.text =
            if (nomUtilisateur.isNotBlank()) {
                "Bienvenue $nomUtilisateur ! Voici nos produits"
            } else {
                "Notre catalogue"
            }

        tvCatalogTitle.visibility = View.VISIBLE
        listViewProducts.visibility = View.VISIBLE

        chargerProduits()

        listViewProducts.adapter = ProduitAdapter()

        listViewProducts.setOnItemClickListener { _, _, position, _ ->
            val produit = produits[position]
            Toast.makeText(
                this,
                "${produit.nom} : ${produit.prix} TND",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun chargerProduits() {
        try {
            val jsonString = assets.open("produits.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonObject = JSONObject(jsonString)
            val tableau = jsonObject.getJSONArray("produits")

            produits.clear()

            for (i in 0 until tableau.length()) {
                val objet = tableau.getJSONObject(i)

                produits.add(
                    Produit(
                        nom = objet.getString("nom"),
                        prix = objet.getInt("prix"),
                        image = objet.getString("image")
                    )
                )
            }

        } catch (e: Exception) {
            Log.e("MiniShop", "Erreur de lecture du JSON", e)
            Toast.makeText(
                this,
                "Erreur de chargement des produits",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private inner class ProduitAdapter : BaseAdapter() {

        override fun getCount(): Int = produits.size

        override fun getItem(position: Int): Produit =
            produits[position]

        override fun getItemId(position: Int): Long =
            position.toLong()

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {
            val view = convertView ?: LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.item_product, parent, false)

            val imgProduct =
                view.findViewById<ImageView>(R.id.imgProduct)
            val txtProduct =
                view.findViewById<TextView>(R.id.txtProduct)

            val produit = getItem(position)

            txtProduct.text = "${produit.nom} — ${produit.prix} TND"

            val imageId = resources.getIdentifier(
                produit.image,
                "drawable",
                packageName
            )

            if (imageId != 0) {
                imgProduct.setImageResource(imageId)
            } else {
                imgProduct.setImageDrawable(null)
            }

            return view
        }
    }
}
