package com.tanvir.dogbreedapp.breeds.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tanvir.dogbreedapp.BR
import com.tanvir.dogbreedapp.R
import com.tanvir.dogbreedapp.databinding.BreedListItemBinding


class BreedListAdapter

    () :
    RecyclerView.Adapter<BreedListAdapter.GitRepoViewHolder>()
{

    private var breedList: MutableList<Pair<String,String>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        val viewBinding: BreedListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.breed_list_item, parent, false

        )
        return GitRepoViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return breedList.size
    }

    override fun onBindViewHolder(holder: GitRepoViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setResponse(response: MutableList<Pair<String,String>>) {
        this.breedList = response
        notifyDataSetChanged()
        // notifyDataSetChanged()
    }

    inner class GitRepoViewHolder(private val viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = breedList[position]
            val rowItem = if(row.second.isEmpty()) row.first else row.first+ " -> "+ row.second
            viewBinding.setVariable(BR.breed, rowItem)
            viewBinding.executePendingBindings()
            itemView.setOnClickListener {
            val bundle = bundleOf("parentBreed" to row.first, "childBreed" to row.second)
            itemView.findNavController()
                .navigate(R.id.action_breedListFragment_to_breedImageFragment, bundle)
        }
        }
    }


}

