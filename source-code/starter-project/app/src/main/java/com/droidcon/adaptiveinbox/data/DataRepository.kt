package com.droidcon.adaptiveinbox.data

import com.droidcon.adaptiveinbox.model.DataState
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailType
import com.droidcon.adaptiveinbox.model.MeetingsData
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun getMailsByType(mailType: MailType): Flow<DataState<List<MailData>>>

    suspend fun getMailByMailId(mailId: String): Flow<DataState<MailData>>

    suspend fun getMeetingData(): Flow<DataState<MeetingsData>>
}