package com.example.mealsearch.presentation.meal_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.databinding.ViewHolderSearchListBinding
import com.example.mealsearch.domain.model.Meal

class MealPagingAdapter
//@Inject
constructor(private val onClickListener: OnClickListener)
    :PagingDataAdapter<MealDTO , MealPagingAdapter.MyViewHolder>(Diff()){


    class MyViewHolder(val binding: ViewHolderSearchListBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(image: MealDTO){
                binding.mealDTO = image
            }
        }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val image =getItem(position)

        image?.let {
            holder.bind(image)
        }

        holder.itemView.setOnClickListener{
            onClickListener.onClick(image!!)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        val binding =
            ViewHolderSearchListBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return MyViewHolder(binding)
    }


    class Diff : DiffUtil.ItemCallback<MealDTO>(){
        override fun areItemsTheSame(oldItem: MealDTO, newItem: MealDTO): Boolean  =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(oldItem: MealDTO, newItem: MealDTO): Boolean =
            oldItem == newItem
    }
    class OnClickListener(val clickListener: (pix: MealDTO) -> Unit) {
        fun onClick(pix: MealDTO) = clickListener(pix)
    }
}