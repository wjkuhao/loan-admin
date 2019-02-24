package com.mod.loan.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Table(name = "tb_manager_company")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_type")
    private Integer companyType;

    @Column(name = "company_status")
    private Integer companyStatus;

    private String remark;

    @Column(name = "company_phone")
    private String companyPhone;

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
     * @return company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return company_address
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * @param companyAddress
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    /**
     * @return company_type
     */
    public Integer getCompanyType() {
        return companyType;
    }

    /**
     * @param companyType
     */
    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    /**
     * @return company_status
     */
    public Integer getCompanyStatus() {
        return companyStatus;
    }

    /**
     * @param companyStatus
     */
    public void setCompanyStatus(Integer companyStatus) {
        this.companyStatus = companyStatus;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return company_phone
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     * @param companyPhone
     */
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
}