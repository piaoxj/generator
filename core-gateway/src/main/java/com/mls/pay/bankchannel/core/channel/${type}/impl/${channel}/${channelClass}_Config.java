package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel};

import com.mls.pay.bankchannel.core.utils.ChannelConfig;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_Config extends ChannelConfig {

    private String version;
    private String notifyUrl;
    private String pageUrl;
    private String refNotifyUrl;
    private String payUri;
    private String refundUri;
    private String queryUri;
    private String queryRefundUri;



    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getRefNotifyUrl() {
        return refNotifyUrl;
    }

    public void setRefNotifyUrl(String refNotifyUrl) {
        this.refNotifyUrl = refNotifyUrl;
    }

    public String getPayUri() {
        return payUri;
    }

    public void setPayUri(String payUri) {
        this.payUri = payUri;
    }

    public String getRefundUri() {
        return refundUri;
    }

    public void setRefundUri(String refundUri) {
        this.refundUri = refundUri;
    }

    public String getQueryUri() {
        return queryUri;
    }

    public void setQueryUri(String queryUri) {
        this.queryUri = queryUri;
    }

    public String getQueryRefundUri() {
        return queryRefundUri;
    }

    public void setQueryRefundUri(String queryRefundUri) {
        this.queryRefundUri = queryRefundUri;
    }


}
