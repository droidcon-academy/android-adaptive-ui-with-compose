package com.droidcon.adaptiveinbox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.adaptiveinbox.data.DataRepository
import com.droidcon.adaptiveinbox.model.DataState
import com.droidcon.adaptiveinbox.model.MailRepliesData
import com.droidcon.adaptiveinbox.model.MailType
import com.droidcon.adaptiveinbox.model.PaneType
import com.droidcon.adaptiveinbox.ui.state.MailUIState
import com.droidcon.adaptiveinbox.ui.state.MeetingsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MailViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _mailUiState = MutableStateFlow(MailUIState(isLoading = true))
    val mailUiState: StateFlow<MailUIState> = _mailUiState

    private val _meetingsUiState = MutableStateFlow(MeetingsUIState(isLoading = true))
    val meetingsUiState: StateFlow<MeetingsUIState> = _meetingsUiState

    fun getMails(mailType: MailType, paneType: PaneType) {
        viewModelScope.launch {
            dataRepository.getMailsByType(mailType = mailType)
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _mailUiState.update {
                                it.copy(
                                    mailList = dataState.data,
                                    isLoading = false,
                                    selectedMail = if (paneType != PaneType.SINGLE_PANE) {
                                        dataState.data.firstOrNull()
                                    } else {
                                        it.selectedMail
                                    },
                                    selectedMessageForAttachments = mailUiState.value.selectedMessageForAttachments,
                                )
                            }
                        }

                        else -> {
                        }
                    }
                }.collect()
        }
    }

    fun openMailDetailsScreen(mailId: String) {
        viewModelScope.launch {
            dataRepository.getMailByMailId(mailId = mailId)
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _mailUiState.update {
                                it.copy(
                                    selectedMail = dataState.data
                                )
                            }
                        }

                        else -> {}
                    }
                }.collect()
        }
    }

    fun closeMailDetailsScreen() {
        _mailUiState.update {
            it.copy(
                selectedMail = null,
            )
        }
    }

    fun openAttachmentDetailsScreen(
        selectedMessageForAttachments: MailRepliesData
    ) {
        _mailUiState.update {
            it.copy(
                selectedMessageForAttachments = selectedMessageForAttachments
            )
        }
    }

    fun closeAttachmentDetailsScreen() {
        _mailUiState.update {
            it.copy(
                selectedMessageForAttachments = null
            )
        }
    }

    fun getMeetingsData() {
        viewModelScope.launch {

            dataRepository.getMeetingData().onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _meetingsUiState.update {
                            it.copy(
                                isLoading = true,
                                meetings = emptyList(),
                                carousel = emptyList()
                            )
                        }
                    }

                    is DataState.Success -> {
                        _meetingsUiState.update {
                            it.copy(
                                isLoading = false,
                                meetings = dataState.data.meetings,
                                carousel = dataState.data.carousel
                            )
                        }
                    }

                    else -> {}
                }
            }.collect()
        }
    }
}