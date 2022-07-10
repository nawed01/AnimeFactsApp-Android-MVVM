package com.nawed.animefacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nawed.animefacts.adapter.AnimeListAdapter
import com.nawed.animefacts.databinding.FragmentAllAnimeBinding
import com.nawed.animefacts.models.DataX
import com.nawed.animefacts.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllAnimeFragment : Fragment() {

    private var _binding: FragmentAllAnimeBinding?  = null
    private val binding get() = _binding!! // null proof
    private val animeFactsViewModel by viewModels<AnimeFactsViewModel>()
    private var animeListAdapter: AnimeListAdapter? = null
    // This automatically calls viewModel provider class

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllAnimeBinding.inflate(inflater,container,false)

        animeFactsViewModel.fetchAnimeList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // When view is created then bind your observers
        animeFactsViewModel.animeResponseLiveData.observe(viewLifecycleOwner) {
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

    private fun displayData(data: List<DataX>?) {
        if (!data.isNullOrEmpty()) {
            binding.textViewErrorMsg.visibility = View.GONE
            binding.recyclerViewAllAnime.visibility = View.VISIBLE

            binding.recyclerViewAllAnime.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
            animeListAdapter = AnimeListAdapter(data,parentFragmentManager)
            binding.recyclerViewAllAnime.adapter = animeListAdapter
            binding.recyclerViewAllAnime.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            binding.recyclerViewAllAnime.setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        // just to increase performance
        // free up memory
        super.onDestroyView()
        _binding = null
    }

}