package templateJobTower

data class Inventory(
    val description: String,
    val has_active_failures: Boolean,
    val has_inventory_sources: Boolean,
    val hosts_with_active_failures: Int,
    val id: Int,
    val inventory_sources_with_failures: Int,
    val kind: String,
    val name: String,
    val organization_id: Int,
    val total_groups: Int,
    val total_hosts: Int,
    val total_inventory_sources: Int
)