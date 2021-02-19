package templateWorkflowTower

data class Related(
    val access_list: String,
    val activity_stream: String,
    val copy: String,
    val created_by: String,
    val labels: String,
    val last_job: String,
    val launch: String,
    val modified_by: String,
    val notification_templates_approvals: String,
    val notification_templates_error: String,
    val notification_templates_started: String,
    val notification_templates_success: String,
    val object_roles: String,
    val organization: String,
    val schedules: String,
    val survey_spec: String,
    val webhook_key: String,
    val webhook_receiver: String,
    val workflow_jobs: String,
    val workflow_nodes: String
)