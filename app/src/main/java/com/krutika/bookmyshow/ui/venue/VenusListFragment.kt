package com.krutika.bookmyshow.ui.venue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.krutika.bookmyshow.ui.MainViewModel
import com.krutika.bookmyshow.ui.common.BaseFragment
import com.krutika.myapplication.R
import com.krutika.myapplication.databinding.FragmentVenusListBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VenusListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class VenusListFragment : BaseFragment<MainViewModel>() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentVenusListBinding? = null
    private val binding get() = _binding!!
    override fun getViewModel(): MainViewModel {
        return mainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenusListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        buildmodels()
        return binding.root
    }

    fun buildmodels() {
        mainViewModel.getShowtimeList().observe(viewLifecycleOwner, Observer {
            with(binding) {
                val adapter = mainViewModel.showTimeResponse.value?.let { VenueAdapter(it) }
                rvVenue.adapter = adapter
            }
        })
    }

}