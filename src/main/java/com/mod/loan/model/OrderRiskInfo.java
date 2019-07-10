package com.mod.loan.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * tb_order_risk_info
 * @author 
 */
@Table(name = "tb_order_risk_info")
public class OrderRiskInfo implements Serializable {
    @Id
    private Long id;

    private Long orderId;

    /**
     * 风控id
     */
    private String riskId;

    /**
     * 风控状态, 0-未知 1-通过 2-拒绝 3-人审
     */
    private Boolean riskStatus;

    /**
     * 风控结果
     */
    private String riskResult;

    /**
     * 用户手机
     */
    private String userPhone;

    /**
     *  用户姓名
     */
    private String userName;

    /**
     *  身份证号
     */
    private String userCertNo;

    /**
     * 天机、拍拍、天御模型分数JSON串
     */
    private String riskModelScore;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRiskId() {
        return riskId;
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId;
    }

    public Boolean getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(Boolean riskStatus) {
        this.riskStatus = riskStatus;
    }

    public String getRiskResult() {
        return riskResult;
    }

    public void setRiskResult(String riskResult) {
        this.riskResult = riskResult;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCertNo() {
        return userCertNo;
    }

    public void setUserCertNo(String userCertNo) {
        this.userCertNo = userCertNo;
    }

    public String getRiskModelScore() {
        return riskModelScore;
    }

    public void setRiskModelScore(String riskModelScore) {
        this.riskModelScore = riskModelScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}