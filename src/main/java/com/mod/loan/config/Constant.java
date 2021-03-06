package com.mod.loan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Constant {

    public final static String cookie_token = "modid";

    public final static String MOD_PROJECT_NAME = "admin";
    /**
     * 订单状态：等待复审
     */
    public final static Integer ORDER_FOR_AUDITING = 12;

    /**
     * 订单状态：待放款
     */
    public final static Integer ORDER_FOR_LENDING = 21;

    /**
     * 订单状态：放款中
     */
    public final static Integer ORDER_IN_LENDING = 22;

    /**
     * 订单状态：放款失败
     */
    public final static Integer ORDER_LEND_FAIL = 23;

    /**
     * 订单状态：已放款/还款中
     */
    public final static Integer ORDER_IN_REPAYING = 31;

    /**
     * 订单状态：逾期
     */
    public final static Integer ORDER_IS_OVERDUE = 33;

    /**
     * 订单状态：坏账
     */
    public final static Integer ORDER_BAD_LOAN = 34;

    /**
     * 订单状态：正常还款
     */
    public final static Integer ORDER_REPAYED = 41;

    /**
     * 订单状态：逾期还款
     */
    public final static Integer ORDER_OVERDUE_REPAYED = 42;

    /**
     * 订单状态：复审失败
     */
    public final static Integer ORDER_AUDIT_FAIL = 52;

    public static String env;

    public static String jwt_sercetKey;

    public static String server_itf_url;

    @Value("${server.h5.url}")
    private String server_h5_url;

    public static String oss_static_prefix;

    public static String accesskey_id;

    public static String access_key_secret;

    public static String endpoint_out;

    public static String endpoint_in;

    public static String bucket_name;

    public static String moxie_token;

    public static String moxie_zfb_zmscore;

    public static String baidu_ak_value;

    public static String baidu_sn_value;

    public static String moxie_bucket_name;

    public static String moxie_mobile_jxl;

    public static String moxie_mobile_mxdata;

    public static String moxie_zfb_data;

    public static String moxie_magic_wand;

    public static String moxie_address_list;

    /**
     * 商户最大综合费率限制
     */
    public static Integer MERCHANT_MAX_PRODUCT_TOTAL_RATE;

    /**
     * 商户最大逾期费率限制
     */
    public static Integer MERCHANT_MAX_PRODUCT_OVERDUE_RATE;

    /**
     * 商户最大额度限制
     */
    public static Integer MERCHANT_MAX_PRODUCT_MONEY;

    @Value("${oss.static.prefix:}")
    public void setOss_static_prefix(String oss_static_prefix) {
        Constant.oss_static_prefix = oss_static_prefix;
    }

    @Value("${oss.moxie.bucket.name:}")
    public void setMoxie_bucket_name(String moxie_bucket_name) {
        Constant.moxie_bucket_name = moxie_bucket_name;
    }

    @Value("${oss.moxie.bucket.name.mobile_jxl:}")
    public void setMoxie_mobile_jxl(String moxie_mobile_jxl) {
        Constant.moxie_mobile_jxl = moxie_mobile_jxl;
    }

    @Value("${oss.moxie.bucket.name.mobile_mxdata:}")
    public void setMoxie_mobile_mxdata(String moxie_mobile_mxdata) {
        Constant.moxie_mobile_mxdata = moxie_mobile_mxdata;
    }

    @Value("${oss.moxie.bucket.name.zfb_data:}")
    public void setMoxie_zfb_data(String moxie_zfb_data) {
        Constant.moxie_zfb_data = moxie_zfb_data;
    }

    @Value("${oss.moxie.bucket.name.magic_wand:}")
    public void setMoxie_magic_wand(String moxie_magic_wand) {
        Constant.moxie_magic_wand = moxie_magic_wand;
    }

    @Value("${oss.moxie.bucket.name.address_list:}")
    public void setMoxie_address_list(String moxie_address_list) {
        Constant.moxie_address_list = moxie_address_list;
    }

    @Value("${oss.accesskey.id:}")
    public void setAccesskey_id(String accesskey_id) {
        Constant.accesskey_id = accesskey_id;
    }

    @Value("${oss.accesskey.secret:}")
    public void setAccess_key_secret(String access_key_secret) {
        Constant.access_key_secret = access_key_secret;
    }

    @Value("${environment:}")
    public void setEnv(String env) {
        Constant.env = env;
    }

    @Value("${jwt.sercetKey:}")
    public void setJwt_sercetKey(String jwt_sercetKey) {
        Constant.jwt_sercetKey = jwt_sercetKey;
    }

    @Value("${server.itf.url:}")
    public void setServer_itf_url(String server_itf_url) {
        Constant.server_itf_url = server_itf_url;
    }

    @Value("${moxie.token:}")
    public void setMoxie_token(String moxie_token) {
        Constant.moxie_token = moxie_token;
    }

    @Value("${moxie.zfb.zmscore:}")
    public void setMoxie_zfb_zmscore(String moxie_zfb_zmscore) {
        Constant.moxie_zfb_zmscore = moxie_zfb_zmscore;
    }

    @Value("${baidu_ak_value:}")
    public void setBaidu_ak_value(String baidu_ak_value) {
        Constant.baidu_ak_value = baidu_ak_value;
    }

    @Value("${baidu_sn_value:}")
    public void setBaidu_sn_value(String baidu_sn_value) {
        Constant.baidu_sn_value = baidu_sn_value;
    }

    @Value("${oss.endpoint.out:}")
    public void setEndpoint_out(String endpoint_out) {
        Constant.endpoint_out = endpoint_out;
    }

    @Value("${oss.endpoint.in:}")
    public void setEndpoint_in(String endpoint_in) {
        Constant.endpoint_in = endpoint_in;
    }

    @Value("${oss.static.bucket.name:}")
    public void setBucket_name(String bucket_name) {
        Constant.bucket_name = bucket_name;
    }

    @Value("${merchant.max.product.total.rate:30}")
    public void setMerchantMaxProductTotalRate(Integer merchantMaxProductTotalRate) {
        if (merchantMaxProductTotalRate > 35) {
            merchantMaxProductTotalRate = 35;
        }
        MERCHANT_MAX_PRODUCT_TOTAL_RATE = merchantMaxProductTotalRate;
    }

    @Value("${merchant.max.product.overdue.rate:6}")
    public void setMerchantMaxProductOverdueRate(Integer merchantMaxProductOverdueRate) {
        if (merchantMaxProductOverdueRate > 10) {
            merchantMaxProductOverdueRate = 10;
        }
        MERCHANT_MAX_PRODUCT_OVERDUE_RATE = merchantMaxProductOverdueRate;
    }

    @Value("${merchant.max.product.money:5000}")
    public void setMerchantMaxProductMoney(Integer merchantMaxProductMoney) {
        if (merchantMaxProductMoney > 10000) {
            merchantMaxProductMoney = 10000;
        }
        Constant.MERCHANT_MAX_PRODUCT_MONEY = merchantMaxProductMoney;
    }

    /**
     * 为thymeleaf添加全局静态变量
     *
     * @param viewResolver
     */
    @Bean
    public ThymeleafViewResolver configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if (viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();
            vars.put("ALI_OSS_FILE_URL", oss_static_prefix);
            vars.put("server_h5_url", server_h5_url);
            viewResolver.setStaticVariables(vars);
        }
        return viewResolver;
    }

}
