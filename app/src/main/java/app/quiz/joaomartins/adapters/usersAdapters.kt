package app.quiz.joaomartins.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.quiz.joaomartins.R
import app.quiz.joaomartins.model.Users
import kotlinx.android.synthetic.main.item_history.view.*

class UsersAdapters(private val users: List<Users>) : RecyclerView.Adapter<UsersAdapters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_history, parent, false
        )

        return ViewHolder(
            view
        )

    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = users[position]
        val hitsPercent = user.hits.toInt() * 10

        holder.name.text = user.user
        holder.hits.text = "Pontuação de $hitsPercent%"

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name : TextView = itemView.name
        val hits : TextView = itemView.hits

    }

}