package app.quiz.joaomartins.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import app.quiz.joaomartins.R
import app.quiz.joaomartins.view.QuizActivity
import butterknife.BindView
import butterknife.ButterKnife

class HomeFragments : Fragment() {

    /*
        Widgets
    */
    private lateinit var root: View

    @BindView(R.id.button_quiz)
    lateinit var pageButton: Button


    /*
        Layout
    */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)

        ButterKnife.bind(this, root)


        pageButton.setOnClickListener {

            val intent = Intent(context, QuizActivity::class.java)
            startActivity(intent)

        }


        return root

    }

}