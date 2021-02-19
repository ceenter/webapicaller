package templateWorkflowTower

data class TowerWorkflow(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)