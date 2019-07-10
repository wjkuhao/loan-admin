package com.mod.loan.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * tb_merchant_fee
 * @author 
 */
@Table(name = "tb_merchant_fee")
public class MerchantFee implements Serializable {
    /**
     * 商户号
     */
    @Id
    private String merchantAlias;

    /**
     * 短信1创蓝单价
     */
    private BigDecimal sms1Price;

    /**
     * 短信2飞鸽单价
     */
    private BigDecimal sms2Price;

    /**
     * 有盾单价
     */
    private BigDecimal youdunPrice;

    /**
     * 运营商单价
     */
    private BigDecimal operatorPrice;

    /**
     * 风控单价
     */
    private BigDecimal riskPrice;

    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getMerchantAlias() {
        return merchantAlias;
    }

    public void setMerchantAlias(String merchantAlias) {
        this.merchantAlias = merchantAlias;
    }

    public BigDecimal getSms1Price() {
        return sms1Price;
    }

    public void setSms1Price(BigDecimal sms1Price) {
        this.sms1Price = sms1Price;
    }

    public BigDecimal getSms2Price() {
        return sms2Price;
    }

    public void setSms2Price(BigDecimal sms2Price) {
        this.sms2Price = sms2Price;
    }

    public BigDecimal getYoudunPrice() {
        return youdunPrice;
    }

    public void setYoudunPrice(BigDecimal youdunPrice) {
        this.youdunPrice = youdunPrice;
    }

    public BigDecimal getOperatorPrice() {
        return operatorPrice;
    }

    public void setOperatorPrice(BigDecimal operatorPrice) {
        this.operatorPrice = operatorPrice;
    }

    public BigDecimal getRiskPrice() {
        return riskPrice;
    }

    public void setRiskPrice(BigDecimal riskPrice) {
        this.riskPrice = riskPrice;
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