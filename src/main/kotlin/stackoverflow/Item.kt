package stackoverflow

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("owner")
    @Expose
    var owner: Owner? = null
    @SerializedName("is_accepted")
    @Expose
    var isAccepted: Boolean? = null
    @SerializedName("score")
    @Expose
    var score: Int? = null
    @SerializedName("last_activity_date")
    @Expose
    var lastActivityDate: Int? = null
    @SerializedName("creation_date")
    @Expose
    var creationDate: Int? = null
    @SerializedName("answer_id")
    @Expose
    var answerId: Int? = null
    @SerializedName("question_id")
    @Expose
    var questionId: Int? = null
    @SerializedName("body_markdown")
    @Expose
    val bodyMarkdown: String? = null

}
