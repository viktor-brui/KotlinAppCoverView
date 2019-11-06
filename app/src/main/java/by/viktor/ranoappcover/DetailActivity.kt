package by.viktor.ranoappcover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.viktor.ranoappcover.utils.Common
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //get movie index
        if (intent != null){
            val movie_index = intent.getIntExtra("movie_index", -1)
            if (movie_index != -1)
                loadMovieDetail(movie_index)
        }
    }

    private fun loadMovieDetail(movie_index: Int) {
        val movie = Common.movieLoaded[movie_index]

        Picasso.get().load(movie.image).into(movie_image)
        movie_title.text = movie.name
        movie_description.text = movie.description
    }
}
