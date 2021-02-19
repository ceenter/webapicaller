package templateWorkflowTower

data class LastJob(
    val description: String,
    val failed: Boolean,
    val finished: String,
    val id: Int,
    val name: String,
    val status: String
)