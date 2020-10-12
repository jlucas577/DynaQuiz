package app.quiz.joaomartins.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.quiz.joaomartins.R
import app.quiz.joaomartins.adapters.UsersAdapters
import app.quiz.joaomartins.controller.database.DbHelper
import app.quiz.joaomartins.model.Users
import butterknife.BindView
import butterknife.ButterKnife

class HistoryFragments : Fragment() {

    /*
        Widgets
    */
    private lateinit var root: View

    @BindView(R.id.page_empty)
    lateinit var layoutEmpty: RelativeLayout

    @BindView(R.id.page_loader)
    lateinit var layoutLoader: RelativeLayout

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView


    /*
        Banco de dados
    */
    lateinit var db: DbHelper
    var listUsers : List<Users> = ArrayList<Users>()


    /*
        Layout
    */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_history, container, false)

        ButterKnife.bind(this, root)


        db = DbHelper(this.context!!)


        getUsers()


        return root

    }


    /*
        Funções
    */
    private fun getUsers() {

        listUsers = db.getUsers

        if (listUsers.isNotEmpty()) {

            if (::recyclerView.isInitialized) {

                controllerLayout("content")

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this.context!!)
                    adapter = UsersAdapters(listUsers)
                }

            } else {

                controllerLayout("empty")

            }

        } else {

            controllerLayout("empty")

        }

    }


    /*
        Alterar visibilidade dos widgets
    */
    fun controllerLayout(visible: String) {

        if (visible == "empty") {

            layoutEmpty.visibility = View.VISIBLE
            layoutLoader.visibility = View.GONE
            recyclerView.visibility = View.GONE

        } else if (visible == "loader") {

            layoutEmpty.visibility = View.GONE
            layoutLoader.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE

        } else {

            layoutEmpty.visibility = View.GONE
            layoutLoader.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

        }

    }

}