package com.tanvir.dogbreedapp.breeds.ui


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tanvir.dogbreedapp.R
import com.tanvir.dogbreedapp.databinding.SpecificBreedGridItemBinding
import com.tanvir.dogbreedapp.db.FavBreeds
import java.util.*


class SpecificBreedListAdapter

    () :
    RecyclerView.Adapter<SpecificBreedListAdapter.GitRepoViewHolder>() , Filterable
{

    private var breedList: List<FavBreeds> = mutableListOf()
    private var breedListFiltered: List<FavBreeds> = mutableListOf()
    private lateinit var viewmodel: FavItemViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        val viewBinding: SpecificBreedGridItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.specific_breed_grid_item, parent, false

        )
        return GitRepoViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return breedListFiltered.size
    }

    override fun onBindViewHolder(holder: GitRepoViewHolder, position: Int) {
        holder.onBind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setResponse(viewmodel: FavItemViewModel, response: MutableList<FavBreeds>) {
        this.viewmodel = viewmodel
        this.breedList = response
        this.breedListFiltered = this.breedList
        notifyDataSetChanged()
        // notifyDataSetChanged()
    }


    inner class GitRepoViewHolder(private val viewBinding: SpecificBreedGridItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = breedListFiltered[position]
            Log.i("Image Path", row.imagePath)
            Picasso.get().load(row.imagePath).placeholder(android.R.drawable.sym_def_app_icon).into(viewBinding.image, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {

                }

            })
//            viewBinding.setVariable(BR.breeds, row.name)
//            viewBinding.executePendingBindings()
//            itemView.setOnClickListener {
//
//        }
           viewBinding.buttonFavorite.setOnCheckedChangeListener { compoundButton, b ->

                if(b){
                    viewmodel.insert(FavBreeds(row.imagePath, row.name.replace("/" , "-")))

                }
                else {
                    viewmodel.delete(FavBreeds(row.imagePath , row.name.replace("/" , "-")))
                }
                compoundButton?.startAnimation(createAnimation())

            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                breedListFiltered = if (charSearch.isEmpty()) {
                    breedList
                } else {
                    val resultList = ArrayList<FavBreeds>()
                    for (row in breedList) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = breedListFiltered
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                breedListFiltered = results?.values as ArrayList<FavBreeds>
                notifyDataSetChanged()
            }

        }
    }

    private fun createAnimation() : ScaleAnimation{
        val scaleAnimation = ScaleAnimation(
            0.7f,
            1.0f,
            0.7f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
            Animation.RELATIVE_TO_SELF,
            0.7f
        )
        scaleAnimation.duration = 500
        val bounceInterpolator = BounceInterpolator()
        scaleAnimation.interpolator = bounceInterpolator

     return scaleAnimation
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
}

