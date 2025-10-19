package com.vu.s4660013_assignment2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import s4660013_Assignment2.R
import s4660013_Assignment2.databinding.FragmentS4660013DashboardBinding

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentS4660013DashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var keypass: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentS4660013DashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        keypass = arguments?.getString("keypass") ?: return
        val adapter = DashboardAdapter(emptyList()) { selectedItem ->
            val bundle = Bundle().apply {
                putString("property1", selectedItem.property1)
                putString("property2", selectedItem.property2)
                putString("description", selectedItem.description)
            }
            findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment, bundle)
        }
        binding.recyclerViewEntities.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEntities.adapter = adapter

        viewModel.entities.observe(viewLifecycleOwner) { list ->
            (binding.recyclerViewEntities.adapter as DashboardAdapter).apply {
                (this as DashboardAdapter).notifyDataSetChanged()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBarDashboard.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.loadDashboard(keypass)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}