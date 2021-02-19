package templateWorkflowTower

data class Result(
    val allow_simultaneous: Boolean,
    val ask_inventory_on_launch: Boolean,
    val ask_limit_on_launch: Boolean,
    val ask_scm_branch_on_launch: Boolean,
    val ask_variables_on_launch: Boolean,
    val created: String,
    val description: String,
    val extra_vars: String,
    val id: Int,
    val inventory: Int,
    val last_job_failed: Boolean,
    val last_job_run: String,
    val limit: Any,
    val modified: String,
    val name: String,
    val next_job_run: Any,
    val organization: Int,
    val related: Related,
    val scm_branch: Any,
    val status: String,
    val summary_fields: SummaryFields,
    val survey_enabled: Boolean,
    val type: String,
    val url: String,
    val webhook_credential: Any,
    val webhook_service: String
)