package com.droidcon.adaptiveinbox.model

import androidx.annotation.DrawableRes
import com.droidcon.adaptiveinbox.R
import kotlinx.serialization.Serializable

data class MailData(
    val mailId: String,
    val subject: String,
    val mailType: MailType,
    val isRead: Boolean,
    val isStarred: Boolean,
    val labels: List<MailLabels>,
    val replies: List<MailRepliesData>,
)

data class MailLabels(
    val labelId: String,
    val labelName: String,
    @DrawableRes val labelIconRes: Int,
)

data class MailRepliesData(
    val mailId: String,
    val body: String,
    val senderDetails: AccountDetails,
    val recipientsDetails: List<AccountDetails>,
    val timeMillis: Long,
    val attachments: List<AttachmentDetails>,
)

enum class MailType {
    INBOX, SENT, SPAM, TRASH
}

@Serializable
data class AccountDetails(
    val id: String,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val imageUrl: String,
) {
    val fullName: String = "$firstName $lastName"
}

data class AttachmentDetails(
    val attachmentId: String,
    val attachmentName: String,
    val attachmentSize: Long,
    val attachmentType: String,
    val attachmentUrl: String,
    val attachmentPreviewUrl: String
)

val mailList = listOf(
    MailData(
        mailId = "1",
        subject = "See you at the party!",
        mailType = MailType.INBOX,
        isRead = false,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_event",
                labelName = "Event",
                labelIconRes = R.drawable.mail_label_event
            ),
            MailLabels(
                labelId = "label_communication",
                labelName = "Communication",
                labelIconRes = R.drawable.mail_label_communication
            ),
            MailLabels(
                labelId = "label_finance",
                labelName = "Finance",
                labelIconRes = R.drawable.mail_label_finance
            ),
            MailLabels(
                labelId = "label_personal",
                labelName = "Personal",
                labelIconRes = R.drawable.mail_label_personal
            ),
            MailLabels(
                labelId = "label_reminder",
                labelName = "Reminder",
                labelIconRes = R.drawable.mail_label_reminder
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "1-1",
                body = "Hey, just wanted to confirm that I'll be at the party tomorrow. So let's bring some drinks and snacks, Everyone is coming including John, Sarah, and Mike. Can't wait to see you all!",
                senderDetails = AccountDetails(
                    id = "sender_1",
                    firstName = "John",
                    lastName = "Doe",
                    mail = "john.doe@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Snow",
                        lastName = "White",
                        mail = "snow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = listOf(
                    AttachmentDetails(
                        attachmentId = "attachment_1",
                        attachmentName = "party_invite.pdf",
                        attachmentSize = 1024,
                        attachmentType = "application/pdf",
                        attachmentUrl = "https://example.com/party_invite.pdf",
                        attachmentPreviewUrl = "https://example.com/preview_party_invite.jpg"
                    )
                )
            ),
            MailRepliesData(
                mailId = "1-2",
                body = "Great! I'll bring some snacks.",
                senderDetails = AccountDetails(
                    id = "sender_2",
                    firstName = "Sarah",
                    lastName = "Connor",
                    mail = "sarah.connor@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Sam",
                        lastName = "Kang",
                        mail = "sam@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-3",
                body = "Awesome! I'll bring some drinks.",
                senderDetails = AccountDetails(
                    id = "sender_1",
                    firstName = "John",
                    lastName = "Doe",
                    mail = "john.doe@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Snow",
                        lastName = "White",
                        mail = "snow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-4",
                body = "Perfect! Do we need any decorations?",
                senderDetails = AccountDetails(
                    id = "sender_2",
                    firstName = "Sarah",
                    lastName = "Connor",
                    mail = "sarah.connor@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Sam",
                        lastName = "Kang",
                        mail = "sam@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-5",
                body = "I think we have enough, but a few more balloons wouldn't hurt.",
                senderDetails = AccountDetails(
                    id = "sender_1",
                    firstName = "John",
                    lastName = "Doe",
                    mail = "john.doe@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Snow",
                        lastName = "White",
                        mail = "snow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-6",
                body = "Got it! I'll pick some up on my way.",
                senderDetails = AccountDetails(
                    id = "sender_2",
                    firstName = "Sarah",
                    lastName = "Connor",
                    mail = "sarah.connor@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Sam",
                        lastName = "Kang",
                        mail = "sam@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-7",
                body = "Thanks! See you tomorrow.",
                senderDetails = AccountDetails(
                    id = "sender_1",
                    firstName = "John",
                    lastName = "Doe",
                    mail = "john.doe@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Snow",
                        lastName = "White",
                        mail = "snow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-8",
                body = "See you! Can't wait.",
                senderDetails = AccountDetails(
                    id = "sender_2",
                    firstName = "Sarah",
                    lastName = "Connor",
                    mail = "sarah.connor@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Sam",
                        lastName = "Kang",
                        mail = "sam@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-9",
                body = "Hey, just a quick reminder to bring the drinks.",
                senderDetails = AccountDetails(
                    id = "sender_2",
                    firstName = "Sarah",
                    lastName = "Connor",
                    mail = "sarah.connor@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Sam",
                        lastName = "Kang",
                        mail = "sam@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-10",
                body = "Don't worry, I won't forget!",
                senderDetails = AccountDetails(
                    id = "sender_1",
                    firstName = "John",
                    lastName = "Doe",
                    mail = "john.doe@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Snow",
                        lastName = "White",
                        mail = "snow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-11",
                body = "Great! See you soon.",
                senderDetails = AccountDetails(
                    id = "sender_2",
                    firstName = "Sarah",
                    lastName = "Connor",
                    mail = "sarah.connor@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Sam",
                        lastName = "Kang",
                        mail = "sam@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "1-12",
                body = "See you!",
                senderDetails = AccountDetails(
                    id = "sender_1",
                    firstName = "John",
                    lastName = "Doe",
                    mail = "john.doe@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Snow",
                        lastName = "White",
                        mail = "snow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "2",
        subject = "Meeting Reminder",
        mailType = MailType.INBOX,
        isRead = false,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_urgent",
                labelName = "Urgent",
                labelIconRes = R.drawable.mail_label_urgent
            ),
            MailLabels(
                labelId = "label_communication",
                labelName = "Communication",
                labelIconRes = R.drawable.mail_label_communication
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "2-1",
                body = "Just a quick reminder about our meeting tomorrow at 10 AM. Please be on time.",
                senderDetails = AccountDetails(
                    id = "sender_1",
                    firstName = "John",
                    lastName = "Doe",
                    mail = "john.doe@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Rachel",
                        lastName = "Jackson",
                        mail = "rachel@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = listOf(
                    AttachmentDetails(
                        attachmentId = "attachment_1",
                        attachmentName = "meeting_agenda.pdf",
                        attachmentSize = 2048,
                        attachmentType = "application/pdf",
                        attachmentUrl = "https://example.com/meeting_agenda.pdf",
                        attachmentPreviewUrl = "https://example.com/preview_meeting_agenda.jpg"
                    )
                )
            ),
            MailRepliesData(
                mailId = "2-2",
                body = "Got it. I'll be there on time.",
                senderDetails = AccountDetails(
                    id = "sender_3",
                    firstName = "Mike",
                    lastName = "Smith",
                    mail = "mike.smith@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_1",
                        firstName = "Snow",
                        lastName = "White",
                        mail = "snow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "3",
        subject = "Project Proposal",
        mailType = MailType.INBOX,
        isRead = true,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_work",
                labelName = "Work",
                labelIconRes = R.drawable.mail_label_work
            ),
            MailLabels(
                labelId = "label_important",
                labelName = "Important",
                labelIconRes = R.drawable.mail_label_important
            ),
            MailLabels(
                labelId = "label_contract",
                labelName = "Contract",
                labelIconRes = R.drawable.mail_label_contract
            ),
            MailLabels(
                labelId = "label_followup",
                labelName = "Follow-up",
                labelIconRes = R.drawable.mail_label_followup
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "3-1",
                body = "Here is the draft for the project proposal. Please review and provide feedback.",
                senderDetails = AccountDetails(
                    id = "sender_4",
                    firstName = "Alice",
                    lastName = "Johnson",
                    mail = "alice.johnson@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Bob",
                        lastName = "Brown",
                        mail = "bob.brown@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = listOf(
                    AttachmentDetails(
                        attachmentId = "attachment_2",
                        attachmentName = "project_proposal.docx",
                        attachmentSize = 5120,
                        attachmentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        attachmentUrl = "https://example.com/project_proposal.docx",
                        attachmentPreviewUrl = "https://example.com/preview_project_proposal.jpg"
                    )
                )
            ),
            MailRepliesData(
                mailId = "3-2",
                body = "Just another follow-up. Let me know what you think.",
                senderDetails = AccountDetails(
                    id = "sender_4",
                    firstName = "Ashley",
                    lastName = "Foster",
                    mail = "ashley.foster@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_2",
                        firstName = "Jack",
                        lastName = "Sparrow",
                        mail = "jack.sparrow@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "4",
        subject = "Budget Plan",
        mailType = MailType.INBOX,
        isRead = true,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_work",
                labelName = "Work",
                labelIconRes = R.drawable.mail_label_work
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "4-1",
                body = "Attached is the draft for the budget plan. Let me know if there are any changes needed.",
                senderDetails = AccountDetails(
                    id = "sender_5",
                    firstName = "Josh",
                    lastName = "Roberts",
                    mail = "josh.roberts@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_3",
                        firstName = "David",
                        lastName = "Evans",
                        mail = "david.evans@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = listOf(
                    AttachmentDetails(
                        attachmentId = "attachment_3",
                        attachmentName = "budget_plan.xlsx",
                        attachmentSize = 10240,
                        attachmentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        attachmentUrl = "https://example.com/budget_plan.xlsx",
                        attachmentPreviewUrl = "https://example.com/preview_budget_plan.jpg"
                    )
                )
            ),
            MailRepliesData(
                mailId = "4-2",
                body = "Just confirming the updated estimates.",
                senderDetails = AccountDetails(
                    id = "sender_5",
                    firstName = "Charlie",
                    lastName = "Davis",
                    mail = "charlie.davis@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_3",
                        firstName = "Chris",
                        lastName = "Evans",
                        mail = "chris.evans@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "5",
        subject = "Follow-up on Project",
        mailType = MailType.SENT,
        isRead = true,
        isStarred = true,
        labels = listOf(
            MailLabels(
                labelId = "label_update",
                labelName = "Update",
                labelIconRes = R.drawable.mail_label_update
            ),
            MailLabels(
                labelId = "label_work",
                labelName = "Work",
                labelIconRes = R.drawable.mail_label_work
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "5-1",
                body = "Just following up on the project status. Please provide an update.",
                senderDetails = AccountDetails(
                    id = "sender_6",
                    firstName = "Eve",
                    lastName = "Foster",
                    mail = "eve.foster@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_4",
                        firstName = "Amson",
                        lastName = "Foster",
                        mail = "amson.foster@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "5-2",
                body = "One more follow-up. Please advise.",
                senderDetails = AccountDetails(
                    id = "sender_6",
                    firstName = "Eve",
                    lastName = "Foster",
                    mail = "eve.foster@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_4",
                        firstName = "Susan",
                        lastName = "Lee",
                        mail = "susan.lee@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "6",
        subject = "Invoice for Services",
        mailType = MailType.SENT,
        isRead = true,
        isStarred = true,
        labels = listOf(
            MailLabels(
                labelId = "label_attachments",
                labelName = "Attachments",
                labelIconRes = R.drawable.mail_label_attachments
            ),
            MailLabels(
                labelId = "label_order",
                labelName = "Order",
                labelIconRes = R.drawable.mail_label_order
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "6-1",
                body = "Attached is the invoice for the services provided last month.",
                senderDetails = AccountDetails(
                    id = "sender_7",
                    firstName = "Grace",
                    lastName = "Harris",
                    mail = "grace.harris@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_5",
                        firstName = "Hank",
                        lastName = "Ivy",
                        mail = "hank.ivy@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = listOf(
                    AttachmentDetails(
                        attachmentId = "attachment_4",
                        attachmentName = "invoice.pdf",
                        attachmentSize = 3072,
                        attachmentType = "application/pdf",
                        attachmentUrl = "https://example.com/invoice.pdf",
                        attachmentPreviewUrl = "https://example.com/preview_invoice.jpg"
                    )
                )
            ),
            MailRepliesData(
                mailId = "6-2",
                body = "Please see attached revised invoice.",
                senderDetails = AccountDetails(
                    id = "sender_7",
                    firstName = "Grace",
                    lastName = "Harris",
                    mail = "grace.harris@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_5",
                        firstName = "Harley",
                        lastName = "James",
                        mail = "harley.james@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "7",
        subject = "Win a Free iPhone!",
        mailType = MailType.SPAM,
        isRead = false,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_spam",
                labelName = "Spam",
                labelIconRes = R.drawable.mail_label_spam
            ),
            MailLabels(
                labelId = "label_contest",
                labelName = "Contest",
                labelIconRes = R.drawable.mail_label_contest
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "7-1",
                body = "Congratulations! You have won a free iPhone. Click the link to claim your prize.",
                senderDetails = AccountDetails(
                    id = "sender_8",
                    firstName = "Spam",
                    lastName = "Bot",
                    mail = "spam.bot@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_6",
                        firstName = "User",
                        lastName = "One",
                        mail = "user.one@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "7-2",
                body = "Another suspicious offer text...",
                senderDetails = AccountDetails(
                    id = "sender_8",
                    firstName = "Sam",
                    lastName = "Kang",
                    mail = "sam.kang@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_6",
                        firstName = "User",
                        lastName = "One",
                        mail = "user.one@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "8",
        subject = "You've been selected!",
        mailType = MailType.SPAM,
        isRead = false,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_spam",
                labelName = "Spam",
                labelIconRes = R.drawable.mail_label_spam
            ),
            MailLabels(
                labelId = "label_contest",
                labelName = "Contest",
                labelIconRes = R.drawable.mail_label_contest
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "8-1",
                body = "You have been selected for a special offer. Click here to find out more.",
                senderDetails = AccountDetails(
                    id = "sender_9",
                    firstName = "Spam",
                    lastName = "Bot",
                    mail = "spam.bot2@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_7",
                        firstName = "Gary",
                        lastName = "Lee",
                        mail = "gary.lee@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "8-2",
                body = "Still trying to trick you. Beware.",
                senderDetails = AccountDetails(
                    id = "sender_9",
                    firstName = "Spam",
                    lastName = "Bot",
                    mail = "spam.bot2@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_7",
                        firstName = "Gram",
                        lastName = "swo",
                        mail = "gram.swo@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "9",
        subject = "Old Project Files",
        mailType = MailType.TRASH,
        isRead = true,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_generic",
                labelName = "Generic",
                labelIconRes = R.drawable.mail_label_generic
            ),
            MailLabels(
                labelId = "label_attachments",
                labelName = "Attachments",
                labelIconRes = R.drawable.mail_label_attachments
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "9-1",
                body = "These are the old project files that we no longer need.",
                senderDetails = AccountDetails(
                    id = "sender_10",
                    firstName = "Ivy",
                    lastName = "Jones",
                    mail = "ivy.jones@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_8",
                        firstName = "Jack",
                        lastName = "King",
                        mail = "jack.king@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "9-2",
                body = "More outdated files to remove.",
                senderDetails = AccountDetails(
                    id = "sender_10",
                    firstName = "Ivy",
                    lastName = "Jones",
                    mail = "ivy.jones@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_8",
                        firstName = "Susan",
                        lastName = "Jha",
                        mail = "susan.jha@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    ),
    MailData(
        mailId = "10",
        subject = "Unused Resources",
        mailType = MailType.TRASH,
        isRead = true,
        isStarred = false,
        labels = listOf(
            MailLabels(
                labelId = "label_generic",
                labelName = "Generic",
                labelIconRes = R.drawable.mail_label_generic
            ),
            MailLabels(
                labelId = "label_attachments",
                labelName = "Attachments",
                labelIconRes = R.drawable.mail_label_attachments
            )
        ),
        replies = listOf(
            MailRepliesData(
                mailId = "10-1",
                body = "These are the unused resources that can be deleted.",
                senderDetails = AccountDetails(
                    id = "sender_11",
                    firstName = "Liam",
                    lastName = "Miller",
                    mail = "liam.miller@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_9",
                        firstName = "Nina",
                        lastName = "Olsen",
                        mail = "nina.olsen@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            ),
            MailRepliesData(
                mailId = "10-2",
                body = "Extra resources that can be deleted.",
                senderDetails = AccountDetails(
                    id = "sender_11",
                    firstName = "Liam",
                    lastName = "Miller",
                    mail = "liam.miller@gmail.com",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                recipientsDetails = listOf(
                    AccountDetails(
                        id = "recipient_9",
                        firstName = "Nilson",
                        lastName = "oter",
                        mail = "nilson.oter@gmail.com",
                        imageUrl = "https://picsum.photos/200/300"
                    )
                ),
                timeMillis = System.currentTimeMillis(),
                attachments = emptyList()
            )
        )
    )
)
