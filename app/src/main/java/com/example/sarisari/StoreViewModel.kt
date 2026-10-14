package com.example.sarisari

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class UiState {
    object Loading : UiState()
    data class Success(val items: List<StoreItem>, val loadedAt: String) : UiState()
    data class Error(val message: String) : UiState()
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class StoreViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun onQueryChange(q: String) {
        _query.value = q
    }

    val state: StateFlow<UiState> = _query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { q ->
            flow {
                emit(UiState.Loading)
                delay(800)

                val time = SimpleDateFormat(
                    "HH:mm:ss",
                    Locale.getDefault()
                ).format(Date())

                emit(
                    UiState.Success(
                        items = bigStore.filter { it.name.contains(q, ignoreCase = true) },
                        loadedAt = time
                    )
                )
            }.catch { error ->
                emit(UiState.Error(error.message ?: "Something went wrong"))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState.Loading
        )
}
