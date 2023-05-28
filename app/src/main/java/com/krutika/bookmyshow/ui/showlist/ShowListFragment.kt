package com.krutika.bookmyshow.ui.showlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.krutika.bookmyshow.ui.MainViewModel
import com.krutika.bookmyshow.ui.common.BaseFragment
import com.krutika.myapplication.databinding.FragmentShowListBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [ShowListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ShowListFragment : BaseFragment<ShowlistViewModel>() {
    private val mainViewModel: ShowlistViewModel by viewModels()
    private var _binding: FragmentShowListBinding? = null
    private val binding get() = _binding!!

    override fun getViewModel(): ShowlistViewModel {
        return mainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        buildmodels()
        return binding.root
    }


    fun buildmodels() {
        binding.ivBack.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        with(binding) {
            binding.includeVenue.venueItem = mainViewModel.venue.value
            val adapter = ShowListAdapter(mainViewModel.getTimeSlot)
            rvShowlist.adapter = adapter
        }
    }
}