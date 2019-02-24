package com.mod.loan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Table(name = "tb_manager")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "login_password")
    private String loginPassword;

    @Column(name = "user_role_id")
    private Long userRoleId;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_qq")
    private String userQq;

    @Column(name = "user_work_number")
    private String userWorkNumber;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "account_status")
    private Integer accountStatus;

    @Column(name = "account_type")
    private Integer accountType;
    
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
     * @return login_name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return login_password
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * @param loginPassword
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * @return user_role_id
     */
    public Long getUserRoleId() {
        return userRoleId;
    }

    /**
     * @param userRoleId
     */
    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * @return user_phone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return user_qq
     */
    public String getUserQq() {
        return userQq;
    }

    /**
     * @param userQq
     */
    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    /**
     * @return user_work_number
     */
    public String getUserWorkNumber() {
        return userWorkNumber;
    }

    /**
     * @param userWorkNumber
     */
    public void setUserWorkNumber(String userWorkNumber) {
        this.userWorkNumber = userWorkNumber;
    }

    /**
     * @return company_id
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return department_id
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return last_login_time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return last_login_ip
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * @param lastLoginIp
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * @return account_status
     */
    public Integer getAccountStatus() {
        return accountStatus;
    }

    /**
     * @param accountStatus
     */
    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * @return account_type
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * @param accountType
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

}