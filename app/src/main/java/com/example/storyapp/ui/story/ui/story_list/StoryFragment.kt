package com.example.storyapp.ui.story.ui.story_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentProfileBinding
import com.example.storyapp.databinding.FragmentStoryBinding
import com.example.storyapp.model.AuthViewModel
import com.example.storyapp.model.ViewModelFactory
import com.example.storyapp.response.ListStory
import com.example.storyapp.ui.story.ui.profile.ProfileViewModel


class StoryFragment : Fragment() {
    private val storyFragmentViewModel by viewModels<StoryFragmentViewModel> { ViewModelFactory.getInstance(requireActivity()) }
    private var _binding: FragmentStoryBinding? = null
    private lateinit var adapter: StoryListAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = StoryListAdapter(emptyList())
        adapter.notifyDataSetChanged()

        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(activity,layoutManager.orientation)
        binding.apply {
            rvStory.setHasFixedSize(true)
            rvStory.layoutManager = layoutManager
            rvStory.adapter = adapter
            rvStory.addItemDecoration(dividerItemDecoration)
        }

        storyFragmentViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        storyFragmentViewModel.stateSaverUser().observe(viewLifecycleOwner){
                user ->
            if (user.stateLogin){
                storyFragmentViewModel.getStories(user.token)
            }
        }
        storyFragmentViewModel.storyList.observe(viewLifecycleOwner){
            if(it != null){
                binding.rvStory.adapter = StoryListAdapter(it.listStory)
            }else{
                Toast.makeText(activity, getString(R.string.mgs_dataNull), Toast.LENGTH_SHORT)
            }
        }

        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        println("Ini di story fragment bagian storyList")
//
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentStoryBinding.bind(view)
//        adapter = StoryListAdapter(emptyList())
//        adapter.notifyDataSetChanged()
//
//        val layoutManager = LinearLayoutManager(activity)
//        val dividerItemDecoration = DividerItemDecoration(activity,layoutManager.orientation)
//        binding.apply {
//            rvStory.setHasFixedSize(true)
//            rvStory.layoutManager = layoutManager
//            rvStory.adapter = adapter
//            rvStory.addItemDecoration(dividerItemDecoration)
//        }
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean)= if (state) View.VISIBLE else View.GONE
}