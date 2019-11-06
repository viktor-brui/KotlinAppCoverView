package by.viktor.ranoappcover.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import by.viktor.ranoappcover.R
import by.viktor.ranoappcover.models.Movies
import com.squareup.picasso.Picasso

class MovieAdapter(private val context:Context,
                   private val movieList:List<Movies>): BaseAdapter() {
    override fun getView(i: Int, p1: View?, p2: ViewGroup?): View {
        var rowView:View?=null
        if (rowView == null){
            rowView = LayoutInflater.from(context).inflate(R.layout.layout_item, null)

            val name = rowView!!.findViewById<View>(R.id.name) as TextView
            val image = rowView!!.findViewById<View>(R.id.image) as ImageView

            //set data
            name.text = movieList[i].name
            Picasso.get().load(movieList[i].image).into(image)
        }
        return rowView
    }

    override fun getItem(p0: Int): Any {
        return movieList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return movieList.size
    }
}