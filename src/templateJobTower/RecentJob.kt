package templateJobTower

data class RecentJob(
    val canceled_on: Any,
    val finished: String,
    val id: Int,
    val status: String,
    val type: String
)