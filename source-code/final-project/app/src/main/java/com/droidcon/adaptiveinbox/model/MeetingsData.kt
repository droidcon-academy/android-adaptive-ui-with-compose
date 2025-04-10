package com.droidcon.adaptiveinbox.model

import androidx.annotation.RawRes
import com.droidcon.adaptiveinbox.R

data class MeetingsData(
    val meetings: List<Meeting> = emptyList(),
    val carousel: List<Carousel> = emptyList()
)

data class Meeting(
    val meetingsId: String,
    val meetingsTitle: String,
    val meetingTimeMillis: Long
)

data class Carousel(
    val carouselId: String,
    @RawRes val carouselLottieResId: Int,
)

val meetingsData = MeetingsData(
    meetings = listOf(
        Meeting(
            meetingsId = "1",
            meetingsTitle = "Tech Discussion (Retro)",
            meetingTimeMillis = 1672531199000L
        ),
        Meeting(
            meetingsId = "2",
            meetingsTitle = "Team Standup",
            meetingTimeMillis = 1672534799000L
        ),
        Meeting(
            meetingsId = "3",
            meetingsTitle = "Project Kickoff",
            meetingTimeMillis = 1672538399000L
        ),
        Meeting(
            meetingsId = "4",
            meetingsTitle = "Client Call",
            meetingTimeMillis = 1672541999000L
        ),
    ),
    carousel = listOf(
        Carousel(
            carouselId = "1",
            carouselLottieResId = R.raw.meeting_carousel_1
        ),
        Carousel(
            carouselId = "2",
            carouselLottieResId = R.raw.meeting_carousel_2
        )
    )
)