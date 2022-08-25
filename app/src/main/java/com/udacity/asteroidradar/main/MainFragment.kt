package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.network.NASAApiFilter


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = AsteroidRecycler(AsteroidListener { asteroid ->
            this.findNavController().navigate(
                MainFragmentDirections.actionShowDetail(
                    asteroid
                )
            )
        })

        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroidsList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.filterAsteroidsList(
            when (item.itemId) {
                R.id.show_week_menu -> NASAApiFilter.SHOW_WEEK
                R.id.show_today_menu -> NASAApiFilter.SHOW_DAY
                else -> NASAApiFilter.SHOW_ALL
            }
        )
        return true
    }
}
