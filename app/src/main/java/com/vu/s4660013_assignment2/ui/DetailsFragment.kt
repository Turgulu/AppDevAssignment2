package com.vu.s4660013_assignment2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vu.s4660013_assignment2.data.EntityItem
import com.vu.s4660013_assignment2.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the entity from arguments
        val entity = arguments?.getParcelable<EntityItem>("entity")

        if (entity != null) {
            // Display entity details
            binding.entityName.text = entity.name
            binding.entityDescription.text = entity.description
            binding.entityRating.text = "Rating: ${entity.rating}"
        } else {
            // Show error if no entity found
            binding.entityName.text = "Error"
            binding.entityDescription.text = "No entity data found"
            binding.entityRating.text = "Rating: N/A"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}