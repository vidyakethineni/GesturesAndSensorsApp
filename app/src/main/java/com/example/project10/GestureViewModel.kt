package com.example.project10

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/**
 * ViewModel class for managing gesture-related data.
 */
class GestureViewModel:ViewModel() {
    private val _movesListMLD = MutableLiveData<List<String>>(mutableListOf())
    /**
     * LiveData representing the list of gesture movements.
     */
    val movesListMLD:LiveData<List<String>> = _movesListMLD
    /**
     * Updates the list of gesture movements when a new movement is detected.
     */
    fun movementChanged(movement:String){
        val exitingList:MutableList<String> = _movesListMLD.value as MutableList<String>
        // If the list is not empty and the last movement is the same as the new one, do not add it again.
        if (exitingList.isNotEmpty() && exitingList.last() == movement) return
        exitingList.add(movement)
        _movesListMLD.value = exitingList
    }
}