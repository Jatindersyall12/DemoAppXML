package com.demoappxml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.widget.LinearLayout
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var searchView: SearchView
    private lateinit var pagerIndicator: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        viewPager = findViewById(R.id.viewPager)
        searchView = findViewById(R.id.searchView)
        pagerIndicator = findViewById(R.id.pagerIndicator)
        recyclerView = findViewById(R.id.itemsRecyclerView)
        fab = findViewById(R.id.fab)

        // Image Carousel Setup
        val images = listOf(
            R.drawable.scene, R.drawable.scene, R.drawable.scene,
            R.drawable.scene, R.drawable.scene
        )
        viewPager.adapter = ImagePagerAdapter(images)

        // Pager Indicator setup
        val indicator = PagerIndicator(this, pagerIndicator, images.size)
        viewPager.registerOnPageChangeCallback(indicator)

        // Search View setup
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter logic for RecyclerView
                return true
            }
        })

        // RecyclerView setup
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(getItems())
        recyclerView.adapter = adapter

        // Floating Action Button setup
        fab.setOnClickListener {
            // Open bottom sheet or show stats
        }
    }

    private fun getItems(): List<String> {
        // Return some items based on the current page (sample items)
        return listOf("Apple", "Banana", "Orange", "Plum", "Watermelon")
    }
}

class ImagePagerAdapter(private val images: List<Int>) : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    // ViewHolder class for the image view
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // Set image to ImageView
        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}


class PagerIndicator(
    private val context: Context,
    private val pagerIndicator: LinearLayout,
    private val pageCount: Int
) : ViewPager2.OnPageChangeCallback() {

    override fun onPageSelected(position: Int) {
        // Update the indicator dots when page changes
        for (i in 0 until pagerIndicator.childCount) {
            val indicator = pagerIndicator.getChildAt(i)
            indicator.setBackgroundColor(
                if (i == position) context.getColor(R.color.blue) else context.getColor(R.color.gray)
            )
        }
    }
}

class ItemAdapter(private val items: List<String>) : Adapter<ItemAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.itemTitle)
        private val description: TextView = itemView.findViewById(R.id.itemDescription)

        fun bind(item: String) {
            title.text = item
            description.text = "Description of $item"
        }
    }
}
