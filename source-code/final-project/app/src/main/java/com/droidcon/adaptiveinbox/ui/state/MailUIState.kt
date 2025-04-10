package com.droidcon.adaptiveinbox.ui.state

import com.droidcon.adaptiveinbox.model.Carousel
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailRepliesData
import com.droidcon.adaptiveinbox.model.Meeting

data class MailUIState(
    val isLoading: Boolean,
    val mailList: List<MailData> = emptyList(),
    val selectedMail: MailData? = null,
    val selectedMessageForAttachments: MailRepliesData? = null,
)

data class MeetingsUIState(
    val isLoading: Boolean,
    val meetings: List<Meeting> = emptyList(),
    val carousel: List<Carousel> = emptyList()
)