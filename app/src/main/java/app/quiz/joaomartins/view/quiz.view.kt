package app.quiz.joaomartins.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.quiz.joaomartins.R
import app.quiz.joaomartins.controller.QuizController
import butterknife.BindView
import butterknife.ButterKnife

class QuizActivity : AppCompatActivity() {

    /*
        Widgets
    */
    @BindView(R.id.page_container)
    lateinit var pageContainer: RelativeLayout

    @BindView(R.id.page_toolbar)
    lateinit var pageToolbar: Toolbar

    @BindView(R.id.page_error)
    lateinit var pageError: RelativeLayout

    @BindView(R.id.page_loader)
    lateinit var pageLoader: RelativeLayout

    @BindView(R.id.page_content)
    lateinit var pageContent: RelativeLayout

    @BindView(R.id.page_finish)
    lateinit var pageFinish: RelativeLayout

    @BindView(R.id.page_start)
    lateinit var pageStart: RelativeLayout

    @BindView(R.id.page_content_details)
    lateinit var pageContentDetails: LinearLayout

    @BindView(R.id.answer_title)
    lateinit var answerTitle: TextView

    @BindView(R.id.answer_step)
    lateinit var answerStep: TextView

    @BindView(R.id.finish_title)
    lateinit var finishTitle: TextView

    @BindView(R.id.finish_button)
    lateinit var finishButton: Button

    @BindView(R.id.start_button)
    lateinit var startButton: Button

    @BindView(R.id.start_text)
    lateinit var startText: EditText


    /*
        Classes
    */
    lateinit var controller: QuizController


    /*
        Layout
    */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        ButterKnife.bind(this)


        setSupportActionBar(pageToolbar)

        supportActionBar?.apply {
            title = "Dynaquiz"

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        controller = QuizController(
            this,
            pageLoader,
            pageContent,
            answerTitle,
            pageContentDetails,
            pageContainer,
            pageError,
            answerStep,
            pageFinish,
            finishTitle,
            finishButton,
            pageStart,
            startButton,
            startText
        )
        controller.startQuestion()

    }


    /*
        Ciclos de vida
    */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}