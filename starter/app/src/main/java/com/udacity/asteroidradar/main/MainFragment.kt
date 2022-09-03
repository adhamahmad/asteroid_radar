package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)
//        val binding = FragmentMainBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application)
        val viewModelFactory = MaInViewModelFactory(dataSource,application)

         val viewModel: MainViewModel =
            ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)



        val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener {
            viewModel.onAsteroidClicked(it)
        })

        binding.lifecycleOwner = this
        binding.asteroidRecycler.adapter = adapter
        binding.viewModel = viewModel

        Log.i("MainFragment","${viewModel.navigateToAsteroidDetail.value}")

        viewModel.asteroidsList.observe(viewLifecycleOwner,Observer {
            it?.let {
                adapter.submitList(it)
                Log.i("MainFragment","after submit list")
            }
        })

        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner, Observer{ asteroid ->
            if(asteroid!=null) {
                this.findNavController().navigate(
                    com.udacity.asteroidradar.main.MainFragmentDirections.actionShowDetail(asteroid)
                )
                Log.i("MainFragment", "observer")
                viewModel.onAsteroidDetailNavigated()
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
