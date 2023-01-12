package com.example.mealsearch.presentation.meal_search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealsearch.R

import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.databinding.ViewHolderSearchListBinding

class MealPagingAdapter
//@Inject
constructor(private val onClickListener: OnClickListener)
    :PagingDataAdapter<MealDTO , MealPagingAdapter.MyViewHolder>(Diff()){


    class MyViewHolder(val binding: ViewHolderSearchListBinding) :
        RecyclerView.ViewHolder(binding.root){

        private val banner: ImageView = binding.viewHolderImage
        private val title: TextView = binding.viewHolderItemName

            fun bind(
                image: MealDTO,
                onClickListener: OnClickListener){

                banner.transitionName = image.idMeal
                title.transitionName = image.strMeal
                binding.mealDTO = image

                binding.parentLayout.setOnClickListener {
                    onClickListener.onClick(image , banner , title)
                }
            }
        }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val image =getItem(position)

        image?.let {
            holder.bind(image , onClickListener)
        }

//        holder.binding.viewHolderImage.apply {
//            transitionName = "image $position"
//        }
//        holder.itemView.setOnClickListener{
//            onClickListener.onClick(image!! , holder.binding.viewHolderImage, holder.binding.viewHolderItemName)
//        }
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
    class OnClickListener(val clickListener: (pix: MealDTO, ImageView,TextView) -> Unit) {
        fun onClick(pix: MealDTO,
                    banner: ImageView,
                    title: TextView
        ) = clickListener(pix , banner , title)
    }
}