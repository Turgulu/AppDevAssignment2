package com.vu.s4660013_assignment2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vu.s4660013_assignment2.R
import com.vu.s4660013_assignment2.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var adapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        // Get keypass from arguments
        val keypass = arguments?.getString("keypass") ?: ""
        if (keypass.isNotEmpty()) {
            viewModel.fetchDashboardData(keypass)
        } else {
            // Handle error - no keypass provided
            binding.progressBarDashboard.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        adapter = DashboardAdapter(emptyList()) { entity ->
            // Navigate to details screen with entity data
            val bundle = Bundle().apply {
                putParcelable("entity", entity)
            }
            findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment, bundle)
        }

        binding.recyclerViewEntities.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEntities.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.dashboardState.collect { state ->
                when (state) {
                    is DashboardState.Loading -> {
                        binding.progressBarDashboard.visibility = View.VISIBLE
                    }
                    is DashboardState.Success -> {
                        binding.progressBarDashboard.visibility = View.GONE
                        adapter.updateData(state.entities)
                    }
                    is DashboardState.Error -> {
                        binding.progressBarDashboard.visibility = View.GONE
                        // You can show an error message here
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}