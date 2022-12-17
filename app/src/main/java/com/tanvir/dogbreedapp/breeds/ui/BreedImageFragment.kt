@file:Suppress("DEPRECATION")

package com.tanvir.dogbreedapp.breeds.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.tanvir.dogbreedapp.MyApplication
import com.tanvir.dogbreedapp.db.FavBreeds
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tanvir.dogbreedapp.databinding.BreedImageFragmentBinding
import kotlinx.android.synthetic.main.breed_image_fragment.*
import java.io.File
import java.util.*

class BreedImageFragment : Fragment() {




    private lateinit var dataBinding : BreedImageFragmentBinding
    private lateinit var imageUrl: String
    private val favItemViewModel: FavItemViewModel by viewModels {
        FavItemViewModel.FavBreedsViewModelFactory((activity?.application as MyApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = BreedImageFragmentBinding.inflate(inflater,  container, false).apply {
            viewmodel = ViewModelProviders.of(this@BreedImageFragment)[BreedImageViewModel::class.java]
            lifecycleOwner= viewLifecycleOwner
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parentBreed = arguments?.let { BreedImageFragmentArgs.fromBundle(
            it
        ).parentBreed }
        val childBreed = arguments?.let { BreedImageFragmentArgs.fromBundle(
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
        getBreedImage(imagePath)
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
    private fun getBreedImage(imagePath:String) {
        dataBinding.viewmodel?.breedImage(imagePath)?.observe(viewLifecycleOwner) {
            Picasso.get().load(it).into(breed_img, object : Callback {
                override fun onSuccess() {
                    imageUrl = it
                    dataBinding.viewmodel?.isLoading?.postValue(false)
                    loadFavButton(imagePath)

                }

                override fun onError(e: Exception?) {
                    dataBinding.viewmodel?.isLoading?.postValue(false)
                }

            })

        }

    }

    private fun loadFavButton(imagePath: String) {
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

        button_favorite.setOnCheckedChangeListener { compoundButton, b ->

            if(b){
                favItemViewModel.insert(FavBreeds(imageUrl, imagePath.replace("/" , "-")))
            }
            else {
                favItemViewModel.delete(FavBreeds(imageUrl , imagePath.replace("/" , "-")))
            }
            compoundButton?.startAnimation(scaleAnimation)

        }
    }

}
