package templateJobTower

data class Related(
    val access_list: String,
    val activity_stream: String,
    val copy: String,
    val created_by: String,
    val credentials: String,
    val extra_credentials: String,
    val instance_groups: String,
    val inventory: String,
    val jobs: String,
    val labels: String,
    val last_job: String,
    val launch: String,
    val modified_by: String,
    val notification_templates_error: String,
    val notification_templates_started: String,
    val notification_templates_success: String,
    val object_roles: String,
    val organization: String,
    val project: String,
    val schedules: String,
    val slice_workflow_jobs: String,
    val survey_spec: String,
    val webhook_key: String,
    val webhook_receiver: String
)