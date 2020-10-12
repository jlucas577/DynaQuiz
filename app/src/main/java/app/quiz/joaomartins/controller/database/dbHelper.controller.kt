package app.quiz.joaomartins.controller.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import app.quiz.joaomartins.model.Users

class DbHelper (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        //Informações sobre o banco de dados
        private val DB_VERSION = 1
        private val DB_NAME = "DB_USERS.db"

        //Informaçoões sobre a tabela
        private val TB_NAME = "users"

        //Informaçoões sobre as colunas
        private val CL_ID = "id"
        private val CL_USER = "user"
        private val CL_HITS = "hits"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val tableCreate : String = (
                "CREATE TABLE $TB_NAME ($CL_ID INTEGER PRIMARY KEY, $CL_USER TEXT, $CL_HITS TEXT)"
                )

        db!!.execSQL(tableCreate)

    }

    override fun onUpgrade(db: SQLiteDatabase?, versionOld: Int, versionNew: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TB_NAME")
        onCreate(db)

    }

    val getUsers : List<Users>
        get() {

            val listMovies = ArrayList<Users>()
            val selectedQuery = "SELECT * FROM $TB_NAME ORDER BY $CL_ID DESC"
            val db = this.writableDatabase
            val cursor = db.rawQuery(
                selectedQuery
                , null
            )

            if (cursor.moveToFirst()) {

                do {

                    val movie = Users(
                        user = cursor.getString(cursor.getColumnIndex(CL_USER))
                        , hits = cursor.getString(cursor.getColumnIndex(CL_HITS))
                    )

                    listMovies.add(
                        movie
                    )

                } while (cursor.moveToNext())

            }

            return listMovies

        }

    fun newUser(user : Users) {

        val db = this.writableDatabase
        val value = ContentValues()
        value.put(CL_USER, user.user)
        value.put(CL_HITS, user.hits)

        db.insert(
            TB_NAME
            , null
            , value
        )
        db.close()

    }

}