package com.example.minishopmanager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.activity.ComponentActivity

data class Product(
    val name: String,
    val image: Int
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE", "onCreate appelé")

        setContentView(R.layout.activity_main)

        val products = listOf(
            Product("Téléphone", R.drawable.product_phone),
            Product("Casque Bluetooth", R.drawable.product_headphones),
            Product("Montre connectée", R.drawable.product_watch),
            Product("Chargeur USB", R.drawable.product_charger),
            Product("Accessoires", R.drawable.product_case),
            Product("Cosmétiques", R.drawable.product_cosmetics)
        )

        val listView = findViewById<ListView>(R.id.listViewProducts)

        val adapter = ProductAdapter(this, products)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val product = products[position]
            Toast.makeText(
                this,
                "Produit sélectionné : ${product.name}",
                Toast.LENGTH_SHORT
            ).show()
        }


        val btnNext = findViewById<Button>(R.id.btnNext)
        val catalog = findViewById<ListView>(R.id.listViewProducts)
        val catalogTitle = findViewById<TextView>(R.id.tvCatalogTitle)

        btnNext.setOnClickListener {
            catalogTitle.visibility = View.VISIBLE
            catalog.visibility = View.VISIBLE
            btnNext.visibility = View.GONE
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "onStart appelé")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "onResume appelé")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "onPause appelé")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "onStop appelé")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "onDestroy appelé")
    }
}

class ProductAdapter(
    context: Context,
    private val products: List<Product>
) : ArrayAdapter<Product>(context, 0, products) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_product, parent, false)

        val product = products[position]

        val image = view.findViewById<ImageView>(R.id.imgProduct)
        val name = view.findViewById<TextView>(R.id.txtProduct)

        image.setImageResource(product.image)
        name.text = product.name

        return view
    }
}
