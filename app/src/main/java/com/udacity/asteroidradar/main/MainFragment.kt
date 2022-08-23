package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.detail.DetailFragment
import com.udacity.asteroidradar.detail.DetailFragmentArgs

val test = listOf<Asteroid>(
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
    Asteroid(
        id = 0,
        codename = "codename",
        closeApproachDate = "closeApproachDate",
        absoluteMagnitude = 0.0,
        estimatedDiameter = 0.0,
        relativeVelocity = 0.0,
        distanceFromEarth = 0.0,
        isPotentiallyHazardous = false
    ),
)

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = AsteroidRecycler(AsteroidListener { asteroidId ->
            viewModel.onAsteroidClicked(asteroidId)
        })

        binding.asteroidRecycler.adapter = adapter

        adapter.submitList(test)

//        viewModel.asteroidsList.observe(viewLifecycleOwner, Observer {
//            it?.let {
//
//            }
//        })

        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer {asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(
                        Asteroid(
                            id = 0,
                            codename = "codename",
                            closeApproachDate = "closeApproachDate",
                            absoluteMagnitude = 0.0,
                            estimatedDiameter = 0.0,
                            relativeVelocity = 0.0,
                            distanceFromEarth = 0.0,
                            isPotentiallyHazardous = false
                        )
                    )
                )
                viewModel.onAsteroidDetailsNavigated()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}


