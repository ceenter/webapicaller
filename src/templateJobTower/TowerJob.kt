package templateJobTower

data class TowerJob(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)