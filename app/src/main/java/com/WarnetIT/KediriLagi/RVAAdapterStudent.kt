package com.WarnetIT.KediriLagi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.student_list.view.*

class RVAAdapterStudent(private val activity: Activity, private val context: Context, private val arrayList: ArrayList<Kdramas>, private val listFilm: ArrayList<Film>, private val listPage: ArrayList<String>) : RecyclerView.Adapter<RVAAdapterStudent.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.student_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val film = listFilm[position]
        Glide.with(holder.itemView.context)
                .load(arrayList[position].image)
//                .apply(RequestOptions().fitCenter().format(DecodeFormat.PREFER_ARGB_8888).override(Target.SIZE_ORIGINAL))
                .into(holder.view.img_item_photo)


//        holder.view.tv_item_name.text = arrayList[position].nim
        val ratingValue = arrayList[position].rating!!.toFloat()
        holder.view.ratingbar.rating = ratingValue
        holder.view.tv_item_name.text = arrayList[position].judul
        holder.view.tv_item_rating.text = arrayList[position].rating
        holder.view.tv_item_detail.text = arrayList[position].episode

        holder.view.cvList.setOnClickListener {

            var judul = arrayList[position].judul
            if(judul == "Air Terjun Irenggolo"){
                judul = "Air terjun Irenggolo"
            }
            val db = FirebaseFirestore.getInstance()
            db.collection("wisata").document(judul)
                    .update("watch", arrayList[position].watch!!.plus(1))
                    .addOnSuccessListener { result ->
                    }
                    .addOnFailureListener { exception ->
                    }

            val i = Intent(context,DetailFilmActivity::class.java)
            i.putExtra("position",position)
            i.putExtra("judul", arrayList[position].judul)
            i.putExtra("rating", arrayList[position].rating)
            i.putExtra("episode", arrayList[position].episode)
            i.putExtra("sinopsis", arrayList[position].sinopsis)
            i.putExtra("imagePage",arrayList[position].image)
            i.putExtra("detail", arrayList[position].detail)
            i.putExtra("watch", arrayList[position].watch.toString())
            activity.startActivity(i)
            activity.overridePendingTransition(R.anim.enter, R.anim.exit)
        }

    }

    class Holder(val view:View) : RecyclerView.ViewHolder(view)

}