@file:Suppress("DEPRECATION")

package com.tanvir.dogbreedapp.breeds.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanvir.dogbreedapp.R
import com.tanvir.dogbreedapp.databinding.BreedListFragmentBinding
import com.tanvir.dogbreedapp.service.ConnectivityStatus


class BreedListFragment : Fragment() {





    private lateinit var adapter: BreedListAdapter
    private lateinit var dataBinding : BreedListFragmentBinding
    private lateinit var breedList: MutableList<Pair<String,String>>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = BreedListFragmentBinding.inflate(inflater,  container, false).apply {
            viewmodel = ViewModelProviders.of(this@BreedListFragment)[BreedListViewModel::class.java]
            lifecycleOwner= viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnectivity()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupObservers() {
        dataBinding.viewmodel?.dogBreeds?.observe(viewLifecycleOwner) {
            if (it != null)
                breedList = mutableListOf()
                for(breed in it){
                    if(breed.value.isEmpty())
                        breedList.add(Pair(breed.key,""))
//                    else
//                        for (sub_breed in breed.value){
//                            breedList.add(Pair(breed.key,sub_breed))
//                        }
                }
                adapter.setResponse(breedList)

        }

    }

    private fun setupAdapter() {
        val viewModel = dataBinding.viewmodel
        if (viewModel != null) {
            adapter = BreedListAdapter()
            val layoutManager = LinearLayoutManager(activity)
            dataBinding.breedListRv.layoutManager = layoutManager
            dataBinding.breedListRv.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
            dataBinding.breedListRv.adapter = adapter
        }
    }

    private fun checkConnectivity(){
        val connectivity = ConnectivityStatus(requireActivity())
        connectivity.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                setupAdapter()
                setupObservers()
            } else {
                dataBinding.viewmodel?.breedEmpty?.postValue(true)
                dataBinding.viewmodel?.isLoading?.postValue(false)
                dataBinding.tvEmpty.text = getString(R.string.warning_internet)
                dataBinding.retry.setOnClickListener {

                    checkConnectivity()
                }
            }
        }

    }
}
