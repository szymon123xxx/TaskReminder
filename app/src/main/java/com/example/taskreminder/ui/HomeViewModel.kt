package com.example.taskreminder.ui

import androidx.lifecycle.ViewModel
import com.example.taskreminder.data.db.Event
import com.example.taskreminder.data.db.EventDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.sql.Time
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: EventDao
): ViewModel() {

    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Loading)
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()


    init {
        print("HEJ")
//        dao.saveEvent()
    }

    fun addEvent(
        title: String,
        time: String,
    ) {
        dao.saveEvent(
            event = Event(
                title = title,
                time = time
            )
        )
        TODO("Not yet implemented")
    }
}

sealed interface HomeState {
    data class Data(val string: String): HomeState
    data class Error(val string: String): HomeState
    data object Loading: HomeState
}