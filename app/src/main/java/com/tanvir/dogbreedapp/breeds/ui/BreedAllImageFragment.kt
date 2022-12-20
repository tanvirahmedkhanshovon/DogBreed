package com.tanvir.dogbreedapp.breeds.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tanvir.dogbreedapp.MyApplication
import com.tanvir.dogbreedapp.databinding.FavBreedListFragmentBinding
import com.tanvir.dogbreedapp.db.FavBreeds
import java.io.File
import java.util.*


@Suppress("DEPRECATION")
class BreedAllImageFragment : Fragment(){
    private   var adapter: SpecificBreedListAdapter = SpecificBreedListAdapter()
    private lateinit var dataBinding : FavBreedListFragmentBinding
    private lateinit var breedList: MutableList<FavBreeds>
    private val favItemViewModel: FavItemViewModel by viewModels {
        FavItemViewModel.FavBreedsViewModelFactory((activity?.application as MyApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FavBreedListFragmentBinding.inflate(inflater,  container, false).apply {
//            viewmodel = favItemViewModel
            lifecycleOwner= viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         dataBinding.viewmodel = favItemViewModel
        val parentBreed = arguments?.let { BreedAllImageFragmentArgs.fromBundle(
            it
        ).parentBreed }
        val childBreed = arguments?.let { BreedAllImageFragmentArgs.fromBundle(
            it
        ).childBreed }
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Your Title"
        var imagePath=""+parentBreed
        if(!childBreed.isNullOrEmpty()){
            imagePath+=(File.separator+childBreed)
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.title = imagePath.trim().uppercase(
            Locale.ROOT
        )


        setupObservers(parentBreed!!)
        setupAdapter()



    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupObservers(imagePath : String) {

        dataBinding.viewmodel?.specificBreedImageList(imagePath)?.observe(viewLifecycleOwner) {

            if (it!=null)
                breedList = mutableListOf()
            for(breed in it){
                  val bredDetails = FavBreeds(breed , imagePath)
                    breedList.add(bredDetails)
            }

            adapter.setResponse(favItemViewModel, breedList)
            dataBinding.viewmodel?.isLoading?.postValue(false)
            dataBinding.searchView.visibility = View.GONE

        }
    }

    private fun setupAdapter() {
        //val viewModel = dataBinding.viewmodel

//        if (viewModel != null) {
            val layoutManager = GridLayoutManager(activity , 2)
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
