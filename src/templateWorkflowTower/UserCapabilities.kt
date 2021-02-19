package templateWorkflowTower

data class UserCapabilities(
    val copy: Boolean,
    val delete: Boolean,
    val edit: Boolean,
    val schedule: Boolean,
    val start: Boolean
)