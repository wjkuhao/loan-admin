package com.mod.loan.model;

import javax.persistence.*;

/**
 * @author wj
 */
@Table(name = "tb_merchant_config")
public class MerchantConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String merchant;
    /**
     * 默认风控token
     */
    @Column(name = "mx_risk_token")
    private String mxRiskToken;
    /**
     * 老客风控token
     */
    @Column(name = "mx_risk_renew_token")
    private String mxRiskRenewToken;
    /**
     * h5地址
     */
    @Column(name = "h5_url")
    private String h5Url;
    /**
     * 加入黑名单逾期天数
     */
    @Column(name = "overdue_blacklist_day")
    private Integer overdueBlacklistDay;
    /**
     * // 地址、公司拒绝关键字
     */
    @Column(name = "reject_keyword")
    private String rejectKeyword;
    /**
     * 认证失效天数
     */
    @Column(name = "ident_invalid_day")
    private Integer identInvalidDay;
    /**
     * 自动提单 0 no 1 yes
     */
    @Column(name = "auto_apply_order")
    private Integer autoApplyOrder;
    /**
     * 客服电话
     */
    @Column(name = "service_phone")
    private String servicePhone;
    /**
     * 自然流量注册 0 拒绝 1 允许
     */
    @Column(name = "default_origin_status")
    private Integer defaultOriginStatus;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private String updateTime;
    /**
     * 最大逾期费费率
     */
    @Column(name = "max_overdue_fee_rate")
    private Integer maxOverdueFeeRate;
    /**
     * 是否需要放款，0：不需要，1：需要
     */
    @Column(name = "user_pay_confirm")
    private Integer userPayConfirm;

    @Column(name =  "multi_loan_merchant")
    private String multiLoanMerchant;

    @Column(name = "multi_loan_count")
    private Integer multiLoanCount;

    @Column(name = "old_customer_risk")
    private Integer oldCustomerRisk;

    @Column(name = "promote_quota_type")
    private Integer promoteQuotaType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getMxRiskToken() {
        return mxRiskToken;
    }

    public void setMxRiskToken(String mxRiskToken) {
        this.mxRiskToken = mxRiskToken;
    }

    public String getMxRiskRenewToken() {
        return mxRiskRenewToken;
    }

    public void setMxRiskRenewToken(String mxRiskRenewToken) {
        this.mxRiskRenewToken = mxRiskRenewToken;
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

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public Integer getOverdueBlacklistDay() {
        return overdueBlacklistDay;
    }

    public void setOverdueBlacklistDay(Integer overdueBlacklistDay) {
        this.overdueBlacklistDay = overdueBlacklistDay;
    }

    public String getRejectKeyword() {
        return rejectKeyword;
    }

    public void setRejectKeyword(String rejectKeyword) {
        this.rejectKeyword = rejectKeyword;
    }

    public Integer getIdentInvalidDay() {
        return identInvalidDay;
    }

    public void setIdentInvalidDay(Integer identInvalidDay) {
        this.identInvalidDay = identInvalidDay;
    }

    public Integer getAutoApplyOrder() {
        return autoApplyOrder;
    }

    public void setAutoApplyOrder(Integer autoApplyOrder) {
        this.autoApplyOrder = autoApplyOrder;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public Integer getDefaultOriginStatus() {
        return defaultOriginStatus;
    }

    public void setDefaultOriginStatus(Integer defaultOriginStatus) {
        this.defaultOriginStatus = defaultOriginStatus;
    }

    public Integer getMaxOverdueFeeRate() {
        return maxOverdueFeeRate;
    }

    public void setMaxOverdueFeeRate(Integer maxOverdueFeeRate) {
        this.maxOverdueFeeRate = maxOverdueFeeRate;
    }

    public Integer getUserPayConfirm() {
        return userPayConfirm;
    }

    public void setUserPayConfirm(Integer userPayConfirm) {
        this.userPayConfirm = userPayConfirm;
    }

    public String getMultiLoanMerchant() {
        return multiLoanMerchant;
    }

    public void setMultiLoanMerchant(String multiLoanMerchant) {
        this.multiLoanMerchant = multiLoanMerchant;
    }

    public Integer getMultiLoanCount() {
        return multiLoanCount;
    }

    public void setMultiLoanCount(Integer multiLoanCount) {
        this.multiLoanCount = multiLoanCount;
    }

    public Integer getOldCustomerRisk() {
        return oldCustomerRisk;
    }

    public void setOldCustomerRisk(Integer oldCustomerRisk) {
        this.oldCustomerRisk = oldCustomerRisk;
    }

    public Integer getPromoteQuotaType() {
        return promoteQuotaType;
    }

    public void setPromoteQuotaType(Integer promoteQuotaType) {
        this.promoteQuotaType = promoteQuotaType;
    }
}
