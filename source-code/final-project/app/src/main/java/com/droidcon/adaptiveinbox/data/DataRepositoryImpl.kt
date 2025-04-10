package com.droidcon.adaptiveinbox.data

import com.droidcon.adaptiveinbox.model.DataState
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailType
import com.droidcon.adaptiveinbox.model.MeetingsData
import com.droidcon.adaptiveinbox.model.mailList
import com.droidcon.adaptiveinbox.model.meetingsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataRepositoryImpl : DataRepository {

    override suspend fun getMailsByType(mailType: MailType) = flow<DataState<List<MailData>>> {
        emit(DataState.Loading())
        val filteredList = mailList.filter {
            it.mailType == mailType
        }

        if (filteredList.isNotEmpty()) {
            emit(DataState.Success(filteredList))
        } else {
            emit(DataState.Error("No mails found"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMailByMailId(mailId: String) = flow<DataState<MailData>> {
        emit(DataState.Loading())
        val mail = mailList.find { it.mailId == mailId }

        if (mail != null) {
            emit(DataState.Success(mail))
        } else {
            emit(DataState.Error("Mail not found"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMeetingData() = flow<DataState<MeetingsData>> {
        emit(DataState.Loading())
        emit(DataState.Success(meetingsData))
    }.flowOn(Dispatchers.IO)
}