package app.quiz.joaomartins.controller.requests

import app.quiz.joaomartins.model.Answer
import app.quiz.joaomartins.model.Question
import retrofit2.Call
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {

    @GET("/question")
    fun getQuestion(): Call<Answer>

    @POST("/answer")
    fun checkAnswer(@Query("questionId") postfix: String, @Body params: RequestBody): Call<Question>

}