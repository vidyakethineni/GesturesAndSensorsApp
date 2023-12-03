package com.example.project10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
/**
 * Fragment to display a log of user gestures using a RecyclerView.
 */
class BottomFragment : Fragment(){
    private lateinit var viewModel:GestureViewModel
    private lateinit var recyclerView: RecyclerView
    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         return inflater.inflate(R.layout.fragment_bottom,container,false)
     }
    /**
     * Initializes the fragment and sets up the RecyclerView for displaying user gestures.
     */
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         viewModel = ViewModelProvider(requireActivity())[GestureViewModel::class.java]

         recyclerView = view.findViewById(R.id.logRecyclerView)
         val adapter = GestureAdapter(mutableListOf())
         recyclerView.adapter = adapter
         viewModel.movesListMLD.observe(viewLifecycleOwner){
             adapter.submitList(it)
         }
     }
}