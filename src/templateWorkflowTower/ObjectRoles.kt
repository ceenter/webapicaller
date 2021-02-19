package templateWorkflowTower

data class ObjectRoles(
    val admin_role: AdminRole,
    val approval_role: ApprovalRole,
    val execute_role: ExecuteRole,
    val read_role: ReadRole
)