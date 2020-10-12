package app.quiz.joaomartins.controller

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.view.View
import android.widget.*
import app.quiz.joaomartins.R
import app.quiz.joaomartins.controller.database.DbHelper
import app.quiz.joaomartins.controller.global.Global
import app.quiz.joaomartins.controller.requests.ApiService
import app.quiz.joaomartins.model.Answer
import app.quiz.joaomartins.model.Question
import app.quiz.joaomartins.model.Users
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.json.JSONObject
import okhttp3.RequestBody

class QuizController(
    private val context: Context,
    private val layoutLoader: RelativeLayout,
    private val layoutContent: RelativeLayout,
    private val answerTitle: TextView,
    private val answerOptions: LinearLayout,
    private val answerContainer: RelativeLayout,
    private val answerError: RelativeLayout,
    private val answerStep: TextView,
    private val layoutFinish: RelativeLayout,
    private val finishTitle: TextView,
    private val finishButton: Button,
    private val layoutStart: RelativeLayout,
    private val startButton: Button,
    private val startText: EditText
) {

    /*
        Classes
    */
    private lateinit var retrofit: Retrofit
    private lateinit var api: ApiService


    /*
        Banco de dados
    */
    lateinit var db: DbHelper


    /*
        Variáveis globais
    */
    var stepQuestion = 1
    var stepId = "0"
    var stepButton = true
    var globalHits = 0
    var globalUser = "Unknow"


    /*
        Página de início
    */
    fun startQuestion() {

        controllerLayout("start")


        startButton.setOnClickListener {

            globalUser = startText.text.toString()


            showMessage("Seja bem-vindo(a), $globalUser!", "welcome")


            getQuestion()

        }

    }


    /*
        Ir para a próxima pergunta
    */
    fun nextQuestion(question: Question) {

        if (question.result) {

            globalHits += 1

            showMessage("Muito bem! Resposta correta!", "success")

        } else {

            showMessage("Resposta incorreta, mas não desanime!", "error")

        }


        if (stepQuestion < 10) {

            stepQuestion += 1


            Handler().postDelayed({
                getQuestion()
            }, 1000)

        } else {

            mountFinish()

        }

    }


    /*
        Pegar uma nova questão
    */
    fun getQuestion() {

        controllerLayout("loader")

        stepButton = false

        answerOptions.removeAllViews()

        retrofit = Retrofit.Builder()
            .baseUrl(Global.urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)

        api.run {
            getQuestion().enqueue(object : Callback<Answer> {

                override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                    mountQuestion(response.body()!!)
                }

                override fun onFailure(call: Call<Answer>, t: Throwable) {
                    controllerLayout("error")
                }

            })
        }


    }


    /*
        Montar layout da questão
    */
    fun mountQuestion(answer: Answer) {

        controllerLayout("success")

        stepId = answer.id
        stepButton = true

        answerTitle.text = answer.statement
        answerStep.text = "$stepQuestion/10"


        for (i in answer.options.indices) {

            val dynamicButton = Button(context)

            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 25)
            dynamicButton.setLayoutParams(params)

            dynamicButton.text = answer.options[i]
            dynamicButton.setTextColor(Color.WHITE)
            dynamicButton.setBackgroundResource(R.drawable.layout_answer)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dynamicButton.stateListAnimator = null
            }

            dynamicButton.setOnClickListener {

                if (stepButton)
                checkQuestion(
                    answer.options[i]
                )

            }

            answerOptions.addView(dynamicButton)

        }

    }


    /*
       Montar tela final
    */
    fun mountFinish() {

        controllerLayout("finish")


        val finishPercent = globalHits * 10
        finishTitle.text = "$finishPercent%"


        db = DbHelper(context)

        val user = Users(
            user = globalUser
            , hits = globalHits.toString()
        )

        db.newUser(
            user
        )


        finishButton.setOnClickListener {
            restartQuestion()
        }

    }


    /*
       Verificar resposta da questão
    */
    fun checkQuestion(answer: String) {

        stepButton = false

        retrofit = Retrofit.Builder()
            .baseUrl(Global.urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api.run {

            val paramObject = JSONObject()
            paramObject.put("answer", answer)

            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                paramObject.toString()
            )

            checkAnswer(stepId, body).enqueue(object : Callback<Question> {

                override fun onResponse(call: Call<Question>, response: Response<Question>) {

                    nextQuestion(response.body()!!)

                }

                override fun onFailure(call: Call<Question>, t: Throwable) {

                    showMessage("Não foi possível verificar a sua resposta, tente novamente!", "error")

                }

            })

        }

    }


    /*
       Reiniciar quiz
    */
    fun restartQuestion() {

        stepQuestion = 1
        stepId = "0"
        stepButton = true
        globalHits = 0

        getQuestion()

    }


    /*
        Alterar visibilidade dos widgets
    */
    fun controllerLayout(visible: String) {

        if (visible == "loader") {

            layoutLoader.visibility = View.VISIBLE
            layoutContent.visibility = View.GONE
            layoutFinish.visibility = View.GONE
            layoutStart.visibility = View.GONE
            answerError.visibility = View.GONE

        } else if (visible == "error") {

            layoutLoader.visibility = View.GONE
            layoutContent.visibility = View.GONE
            layoutFinish.visibility = View.GONE
            layoutStart.visibility = View.GONE
            answerError.visibility = View.VISIBLE

        } else if (visible == "finish") {

            layoutLoader.visibility = View.GONE
            layoutContent.visibility = View.GONE
            layoutFinish.visibility = View.VISIBLE
            layoutStart.visibility = View.GONE
            answerError.visibility = View.GONE

        } else if (visible == "start") {

            layoutLoader.visibility = View.GONE
            layoutContent.visibility = View.GONE
            layoutFinish.visibility = View.GONE
            layoutStart.visibility = View.VISIBLE
            answerError.visibility = View.GONE

        } else {

            layoutLoader.visibility = View.GONE
            layoutContent.visibility = View.VISIBLE
            layoutFinish.visibility = View.GONE
            layoutStart.visibility = View.GONE
            answerError.visibility = View.GONE

        }

    }


    /*
       Mostrar mensagens
    */
    fun showMessage(message: String, type: String){

        val snackbar = Snackbar.make(answerContainer, message, Snackbar.LENGTH_LONG).setAction("Action", null)
        val snackbarView = snackbar.view

        if (type == "error") {
            snackbarView.setBackgroundColor(Color.RED)
        } else if (type == "success") {
            snackbarView.setBackgroundColor(Color.parseColor("#27ae60"))
        }

        snackbar.show()

    }

}