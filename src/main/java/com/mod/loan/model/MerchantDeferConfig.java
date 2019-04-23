package com.mod.loan.model;

import javax.persistence.*;

@Table(name = "tb_merchant_defer_config")
public class MerchantDeferConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String merchant;
    private Integer status;
    private Double dailyDeferRate;
    private Double dailyDeferFee;
    private Double dailyOtherFee;
    private Integer maxDeferTimes;
    private String createTime;
    private String updateTime;
    private String remark;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getDailyDeferRate() {
        return dailyDeferRate;
    }

    public void setDailyDeferRate(Double dailyDeferRate) {
        this.dailyDeferRate = dailyDeferRate;
    }

    public Double getDailyDeferFee() {
        return dailyDeferFee;
    }

    public void setDailyDeferFee(Double dailyDeferFee) {
        this.dailyDeferFee = dailyDeferFee;
    }

    public Double getDailyOtherFee() {
        return dailyOtherFee;
    }

    public void setDailyOtherFee(Double dailyOtherFee) {
        this.dailyOtherFee = dailyOtherFee;
    }

    public Integer getMaxDeferTimes() {
        return maxDeferTimes;
    }

    public void setMaxDeferTimes(Integer maxDeferTimes) {
        this.maxDeferTimes = maxDeferTimes;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
