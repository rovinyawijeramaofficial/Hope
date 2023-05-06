package com.example.mad.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.R
import com.example.mad.model.CharityModel

class CharityAdapter(private val charList:ArrayList<CharityModel>):
    RecyclerView.Adapter<CharityAdapter.viewHolder>(){

    private lateinit var mListner:onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListner: onItemClickListner){
        mListner =clickListner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.char_det,parent,false)
        return viewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder:viewHolder, position: Int) {
        val currentList = charList[position]
        holder.tvCharName.text = currentList.charName
        /*holder.tvCharFou.text = currentList.charFou
        holder.tvCharEmail.text = currentList.charEmail
        holder.tvCharPhone.text = currentList.charPhone*/

    }

    override fun getItemCount(): Int {
        return charList.size
    }

    class viewHolder (itemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val tvCharName : TextView =itemView.findViewById(R.id.charity1)
        /*val tvCharFou: TextView =itemView.findViewById(R.id.rFounder)
        val tvCharEmail: TextView =itemView.findViewById(R.id.rEmail)
        val tvCharPhone: TextView =itemView.findViewById(R.id.rPhone)*/

        init {
            itemView.setOnClickListener {
                clickListner.onItemClick(adapterPosition)
            }
        }

    }



}