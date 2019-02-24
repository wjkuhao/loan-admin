package com.mod.loan.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Table(name = "tb_manager_role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_remark")
    private String roleRemark;

    @Column(name = "role_status")
    private Integer roleStatus;

    @Column(name = "role_type")
    private Integer roleType;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return role_remark
     */
    public String getRoleRemark() {
        return roleRemark;
    }

    /**
     * @param roleRemark
     */
    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    /**
     * @return role_status
     */
    public Integer getRoleStatus() {
        return roleStatus;
    }

    /**
     * @param roleStatus
     */
    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    /**
     * @return role_type
     */
    public Integer getRoleType() {
        return roleType;
    }

    /**
     * @param roleType
     */
    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}