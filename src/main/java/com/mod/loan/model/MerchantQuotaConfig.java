package com.mod.loan.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_merchant_quota_config")
public class MerchantQuotaConfig {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 额度名称
     */
    @Column(name = "quota_name")
    private String quotaName;

    /**
     * 提升额度
     */
    @Column(name = "quota_value")
    private Integer quotaValue;

    /**
     * 字段比较符
     */
    private String comparator;

    /**
     * 预设值
     */
    @Column(name = "preset_value")
    private String presetValue;


    /**
     * 商户别名
     */
    private String merchant;

    /**
     * 1-天机，2-展期次数
     */
    @Column(name = "quota_type")
    private Integer quotaType;

    /**
     * 1-启用, 0-停用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuotaName() {
        return quotaName;
    }

    public void setQuotaName(String quotaName) {
        this.quotaName = quotaName;
    }

    public Integer getQuotaValue() {
        return quotaValue;
    }

    public void setQuotaValue(Integer quotaValue) {
        this.quotaValue = quotaValue;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public String getPresetValue() {
        return presetValue;
    }

    public void setPresetValue(String presetValue) {
        this.presetValue = presetValue;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Integer getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(Integer quotaType) {
        this.quotaType = quotaType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
