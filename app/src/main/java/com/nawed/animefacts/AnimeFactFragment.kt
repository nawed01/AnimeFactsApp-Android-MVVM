package com.nawed.animefacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nawed.animefacts.adapter.AnimeFactListAdapter
import com.nawed.animefacts.databinding.FragmentAnimeFactBinding
import com.nawed.animefacts.models.Data
import com.nawed.animefacts.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeFactFragment : Fragment() {

    private var _binding: FragmentAnimeFactBinding?  = null
    private val binding get() = _binding!!
    private var animeName: String? = null
    private val animeFactsViewModel by viewModels<AnimeFactsViewModel>()
    private var animeFactListAdapter: AnimeFactListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            animeName = requireArguments().getString("anime_name")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeFactBinding.inflate(inflater,container,false)
        animeFactsViewModel.fetchAnimeFactList(animeName!!)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // When view is created then bind your observers
        animeFactsViewModel.factResponseLiveData.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Success -> {
                    // Display the data.
                    displayData(it.data?.data)
                }
                is NetworkResult.Error -> {
                    binding.textViewErrorMsg.text = it.message
                }
                is NetworkResult.Loading -> {
                    // Display the loading bar
                    binding.textViewErrorMsg.text = "Loading...."
                }
            }
        }
    }


    private fun displayData(data: List<Data?>?) {
        if (!data.isNullOrEmpty()) {
            binding.textViewErrorMsg.visibility = View.GONE
            binding.recyclerViewAnimeFacts.visibility = View.VISIBLE

            binding.recyclerViewAnimeFacts.layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL,false)
            animeFactListAdapter = AnimeFactListAdapter(data,parentFragmentManager)
            binding.recyclerViewAnimeFacts.adapter = animeFactListAdapter
            binding.recyclerViewAnimeFacts.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            binding.recyclerViewAnimeFacts.setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        // just to increase performance
        // free up memory
        super.onDestroyView()
        _binding = null
    }
}