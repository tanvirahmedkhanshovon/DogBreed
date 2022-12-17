package com.tanvir.dogbreedapp.breeds.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tanvir.dogbreedapp.MyApplication
import com.tanvir.dogbreedapp.databinding.FavBreedListFragmentBinding
import kotlinx.android.synthetic.main.breed_list_fragment.*
import kotlinx.android.synthetic.main.fav_breed_list_fragment.*


@Suppress("DEPRECATION")
class FavBreedFragment : Fragment(){
    private  var adapter: FavBreedListAdapter = FavBreedListAdapter()
    private lateinit var dataBinding : FavBreedListFragmentBinding
    private val favItemViewModel: FavItemViewModel by viewModels {
        FavItemViewModel.FavBreedsViewModelFactory((activity?.application as MyApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FavBreedListFragmentBinding.inflate(inflater,  container, false).apply {

            lifecycleOwner= viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupAdapter()



    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupObservers() {

        favItemViewModel.allFavBreeds.observe(requireActivity()) { favItemList ->
            favItemViewModel.updateSize()
            Log.i("Size" , "${favItemList?.size}")
            adapter.setResponse(favItemList)
            searching(dataBinding.searchView)
            dataBinding.viewmodel = favItemViewModel



        }
    }
    private fun searching(search: SearchView) {
        search.setOnClickListener {

            search.onActionViewExpanded()
        }
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                 adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }
    private fun setupAdapter() {
        //val viewModel = dataBinding.viewmodel

//        if (viewModel != null) {
            val layoutManager = GridLayoutManager(activity , 3)
        dataBinding.favBreedListRv.layoutManager = layoutManager
        dataBinding.favBreedListRv.setHasFixedSize(true)
        dataBinding.favBreedListRv.adapter = adapter

     //   }
    }
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setHasOptionsMenu(true)
   }
    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

}
