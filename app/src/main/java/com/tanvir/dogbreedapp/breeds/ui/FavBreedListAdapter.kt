package com.tanvir.dogbreedapp.breeds.ui


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tanvir.dogbreedapp.db.FavBreeds
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tanvir.dogbreedapp.BR
import com.tanvir.dogbreedapp.R
import com.tanvir.dogbreedapp.databinding.BreedGridItemBinding
import kotlinx.android.synthetic.main.breed_grid_item.view.*
import kotlinx.android.synthetic.main.breed_image_fragment.*
import kotlinx.android.synthetic.main.breed_image_fragment.view.*
import java.util.*
import kotlin.collections.ArrayList


class FavBreedListAdapter

    () :
    RecyclerView.Adapter<FavBreedListAdapter.GitRepoViewHolder>() , Filterable
{

    private var breedList: List<FavBreeds> = mutableListOf()
    private var breedListFiltered: List<FavBreeds> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        val viewBinding: BreedGridItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.breed_grid_item, parent, false

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
    fun setResponse(response: List<FavBreeds>) {
        this.breedList = response
        this.breedListFiltered = this.breedList
        notifyDataSetChanged()
        // notifyDataSetChanged()
    }

    inner class GitRepoViewHolder(private val viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = breedListFiltered[position]
            Log.i("Image Path", row.imagePath)
            Picasso.get().load(row.imagePath).placeholder(android.R.drawable.sym_def_app_icon).into(viewBinding.root.image, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {

                }

            })
            viewBinding.setVariable(BR.breeds, row.name)
            viewBinding.executePendingBindings()
//            itemView.setOnClickListener {
//
//        }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    breedListFiltered = breedList
                } else {
                    val resultList = ArrayList<FavBreeds>()
                    for (row in breedList) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    breedListFiltered = resultList
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



}

