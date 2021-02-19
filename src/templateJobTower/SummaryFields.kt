package templateJobTower

data class SummaryFields(
    val created_by: CreatedBy,
    val credentials: List<Credential>,
    val inventory: Inventory,
    val labels: Labels,
    val last_job: LastJob,
    val last_update: LastUpdate,
    val modified_by: ModifiedBy,
    val object_roles: ObjectRoles,
    val organization: Organization,
    val project: Project,
    val recent_jobs: List<RecentJob>,
    val survey: Survey,
    val user_capabilities: UserCapabilities
)