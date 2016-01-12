package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel};

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mls.pay.bankchannel.common.dto.gateway.*;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.common.exception.BuildMsgException;
import com.mls.pay.bankchannel.common.exception.ResolveMsgException;
import com.mls.pay.bankchannel.common.util.DateUtil;
import com.mls.pay.bankchannel.common.util.UrlUtils;
import com.mls.pay.bankchannel.core.msg.impl.FreemarkChannelMsgHandlerImpl;
import com.mls.pay.bankchannel.core.utils.ChannelConfig;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_QueryPayMsgHandler extends FreemarkChannelMsgHandlerImpl<QueryPayReqDTO,QueryPayRepDTO> {

    private static final Logger logger = LoggerFactory.getLogger(${channelClass}_QueryPayMsgHandler.class);

    @Override
    protected String getTemplatePath() {
        return "/${type}/${channel}/query.ftl";
    }

    @Override
    public QueryPayReqDTO beforBuildMsg(QueryPayReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {
        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
        try {

            config.setPxfPath(req.getChannelRemark().getPfxPath());

        } catch (Exception e) {
            throw new BuildMsgException(SysRtnCodeEnum.B0001, TradeStatusEnum.FAIL);
        }
        try{
            req.setPostUrl(config.getQueryUri());
            super.put("version", config.getVersion());
            super.put("charset", config.getCharset());
            super.put("merId", req.getChannelRemark().getMerchantNo());
            //super.put("transType", config.getPayTransType());
            super.put("orderTime", DateUtil.DateStampToStringMs(req.getCreateDateTime()));
            super.put("orderNumber", req.getOriBankOrderNo());
        }catch (Exception ex){
            throw new BuildMsgException(SysRtnCodeEnum.B0000, TradeStatusEnum.FAIL);
        }
        return req;
    }
//    @Override
//    public byte[] builderMsg(QueryPayReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {
//        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
//        String privateKey = req.getChannelRemark().getZpk();
//        Map<String, Object> map = new TreeMap<String, Object>();
//        map.putAll(super.getMap());
//        String result = ChinaPayUtils.buildMsg(map, config.getPostUrl(), config.getSignType(), config.getSignature(), config.getSignMethod(), privateKey, config.getCharset());
//        return result.getBytes();
//    }
    @Override
    public QueryPayRepDTO resolveMsg(QueryPayReqDTO req,byte[] rtnMsg, ChannelConfig channelConfig) throws ResolveMsgException {

        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
        String privateKey = req.getChannelRemark().getZpk();
        QueryPayRepDTO queryPayRepDTO = new QueryPayRepDTO();
        String repString = null;
        try {
            repString = StringUtils.toString(rtnMsg, config.getCharset());
        Map<String, Object> respMap = new HashMap<String, Object>();
        boolean flag = false;
//      boolean flag = ChinaPayUtils.verifyResponse(repString, respMap, config.getSignature(), config.getSignMethod(), config.getSignType(), privateKey, config.getCharset());
        if (flag) {
            String queryResult = (String) respMap.get("queryResult");
            String respCode = (String) respMap.get("respCode");
            String respMsg = (String) respMap.get("respMsg");
            String orderNumber = (String) super.get("orderNumber");
            String qid = (String) respMap.get("qid");
            if ("00".equals(respCode)) {
                if ("0".equals(queryResult)) {
                    String settleAmount = (String) respMap.get("settleAmount");
                    if (StringUtils.isNotBlank(settleAmount)) {
                        queryPayRepDTO.setAmount(new Long(settleAmount));
                    }
                        queryPayRepDTO.setRtnCode(BankRtnCodeEnum.S0000.name());
                        queryPayRepDTO.setRtnMsg(BankRtnCodeEnum.S0000.getValue());
                        queryPayRepDTO.setTradeStatus(TradeStatusEnum.SUCCESS);
                        queryPayRepDTO.setOriBankOrderNo(orderNumber);
                        queryPayRepDTO.setBankNo(qid);
                    } else if ("2".equals(queryResult)) {
                        queryPayRepDTO.setRtnCode(BankRtnCodeEnum.P0000.name());
                        queryPayRepDTO.setRtnMsg(BankRtnCodeEnum.P0000.getValue());
                        queryPayRepDTO.setTradeStatus(TradeStatusEnum.PROCESS);
                    }
                } else {
                if ("1".equals(queryResult)) {
                    queryPayRepDTO.setRtnCode(BankRtnCodeEnum.F0000.name());
                    queryPayRepDTO.setRtnMsg(BankRtnCodeEnum.F0000.getValue());
                    queryPayRepDTO.setTradeStatus(TradeStatusEnum.FAIL);
                } else if ("3".equals(queryResult)) {
                    queryPayRepDTO.setRtnCode(BankRtnCodeEnum.F0002.name());
                    queryPayRepDTO.setRtnMsg(BankRtnCodeEnum.F0002.getValue());
                    queryPayRepDTO.setTradeStatus(TradeStatusEnum.FAIL);
                } else {
                    queryPayRepDTO.setRtnCode(BankRtnCodeEnum.S9999.name());
                    queryPayRepDTO.setRtnMsg(BankRtnCodeEnum.S9999.getValue());
                    queryPayRepDTO.setTradeStatus(TradeStatusEnum.UNKNOW);
                }
            }
        } else {
            throw new ResolveMsgException(SysRtnCodeEnum.R0002, TradeStatusEnum.PROCESS);
        }
        } catch (UnsupportedEncodingException e) {
            throw new ResolveMsgException(SysRtnCodeEnum.R0000, TradeStatusEnum.PROCESS);
        }
        return queryPayRepDTO;
    }

}
