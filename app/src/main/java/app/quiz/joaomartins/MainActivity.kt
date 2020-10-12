package app.quiz.joaomartins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    /*
        Widgets
    */
    @BindView(R.id.page_navigation)
    lateinit var pageNavigation: BottomNavigationView


    /*
        Layout
    */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)


        val navController = findNavController(R.id.page_host_fragment)

        pageNavigation.setupWithNavController(navController)

    }

}