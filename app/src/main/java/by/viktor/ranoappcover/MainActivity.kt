package by.viktor.ranoappcover

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast

import by.viktor.ranoappcover.models.Movies
import by.viktor.ranoappcover.utils.IFirebaseLoadDone
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import by.viktor.ranoappcover.utils.Common
import by.viktor.ranoappcover.utils.MovieAdapter
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow

class MainActivity : AppCompatActivity(), IFirebaseLoadDone {
    override fun onFirebaseLoadSuccess(movieList: List<Movies>) {
        dialog.dismiss()
        setupUI()

        Common.movieLoaded = movieList

        adapter = MovieAdapter(this, movieList)

        cover_flow.adapter = adapter
        cover_flow.setOnScrollPositionListener(object:FeatureCoverFlow.OnScrollPositionListener{
            override fun onScrolling() {

            }

            override fun onScrolledToPosition(position: Int) {
                textSwitcher.setText(Common.movieLoaded[position].name)
            }

        })

        cover_flow.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->

            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("movie_index", i)
            startActivity(intent)

        }
    }

    private fun setupUI() {
        setContentView(R.layout.activity_main)
        textSwitcher.setFactory {
            val inflater = LayoutInflater.from(this@MainActivity)
            inflater.inflate(R.layout.layout_title, null) as TextView
        }

        val `in` = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        textSwitcher.inAnimation = `in`
        textSwitcher.outAnimation = out

        //calculate screensize
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val dpHeight = outMetrics.heightPixels/density
        val dpWidth = outMetrics.widthPixels/density

        cover_flow.coverHeight = dpHeight.toInt()
        cover_flow.coverWidth = dpWidth.toInt()

    }

    override fun onFirebaseLoadFailed(message: String) {
        Toast.makeText(this@MainActivity,message,Toast.LENGTH_LONG).show()
    }

    lateinit var ifirebaseLoadDone:IFirebaseLoadDone
    lateinit var dbRef:DatabaseReference
    lateinit var dialog: AlertDialog
    lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ifirebaseLoadDone = this
        loadData()

    }

    private fun loadData() {
//crate dialog
        dialog = SpotsDialog.Builder().setContext(this@MainActivity)
            .setCancelable(false)
            .setMessage("Please wait...").build()
        dialog.show()


        dbRef = FirebaseDatabase.getInstance().getReference("Movies")
        dbRef.addListenerForSingleValueEvent(object:ValueEventListener{

            var movies:MutableList<Movies> = ArrayList()

            override fun onCancelled(p0: DatabaseError) {
                ifirebaseLoadDone.onFirebaseLoadFailed(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (movieSnapShot in p0.children){
                    val movie = movieSnapShot.getValue(Movies::class.java)
                    movies.add(movie!!)
                }
                ifirebaseLoadDone.onFirebaseLoadSuccess(movies)
            }

        })

    }
}
