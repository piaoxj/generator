package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel};
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mls.pay.bankchannel.common.dto.gateway.*;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.common.exception.*;
import com.mls.pay.bankchannel.core.msg.impl.FreemarkChannelMsgHandlerImpl;
import com.mls.pay.bankchannel.core.utils.ChannelConfig;


/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_RefundMsgHandler extends FreemarkChannelMsgHandlerImpl<RefundReqDTO,RefundRepDTO> {

    private static final Logger logger = LoggerFactory.getLogger(${channelClass}_RefundMsgHandler.class);

    @Override
    protected String getTemplatePath() {
        return "/${type}/${channel}/refund.ftl";
    }
    @Override
    public RefundReqDTO beforBuildMsg(RefundReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {

        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;

        try{
            req.setPostUrl(config.getRefundUri());
            super.put("version", config.getVersion());
            super.put("charset", config.getCharset());
            super.put("merId", req.getChannelRemark().getMerchantNo());
            super.put("backEndUrl", config.getNotifyUrl());
             super.put("frontEndUrl", config.getPageUrl());
        }catch (Exception ex){
            throw new BuildMsgException(SysRtnCodeEnum.B0000, TradeStatusEnum.FAIL);
        }
        return req;
     }
//    @Override
//    public byte[] builderMsg(RefundReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {
//        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
//        Map<String, Object> map = new TreeMap<String, Object>();
//        map.putAll(super.getMap());
//        String result = ChinaPayUtils.buildMsg(map, config.getPostUrl(), config.getSignType(), config.getSignature(), config.getSignMethod(), config.getPrivateKey(), config.getCharset());
//        return result.getBytes();
//    }
    @Override
    public RefundRepDTO resolveMsg(RefundReqDTO req,byte[] rtnMsg, ChannelConfig channelConfig) throws ResolveMsgException {

        RefundRepDTO refundRepDTO = new RefundRepDTO();
        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
        String privateKey = req.getChannelRemark().getZpk();
            String repString = null;
        try {
            repString = StringUtils.toString(rtnMsg, config.getCharset());
            Map<String, Object> respMap = new HashMap<String, Object>();
            boolean flag = false;
    //      boolean flag = ChinaPayUtils.verifyResponse(repString,respMap,config.getSignature(),config.getSignMethod(),config.getSignType(),privateKey,config.getCharset());
            if(flag){
                String respCode = (String)respMap.get("respCode");
                String respMsg = (String)respMap.get("respMsg");
                if("00".equals(respCode)){
                    refundRepDTO.setRtnCode(BankRtnCodeEnum.S0000.name());
                    refundRepDTO.setRtnMsg(BankRtnCodeEnum.S0000.getValue());
                    refundRepDTO.setTradeStatus(TradeStatusEnum.SUCCESS);
                }else if("01".equals(respCode)) {

                    refundRepDTO.setRtnCode(BankRtnCodeEnum.P0000.name());
                    refundRepDTO.setRtnMsg(BankRtnCodeEnum.P0000.getValue());
                    refundRepDTO.setTradeStatus(TradeStatusEnum.PROCESS);
                }else if("02".equals(respCode)){
                    refundRepDTO.setRtnCode(BankRtnCodeEnum.F0002.name());
                    refundRepDTO.setRtnMsg(BankRtnCodeEnum.F0002.getValue());
                    refundRepDTO.setTradeStatus(TradeStatusEnum.FAIL);
                }else{
                    refundRepDTO.setRtnCode(BankRtnCodeEnum.S9999.name());
                    refundRepDTO.setRtnMsg(BankRtnCodeEnum.S9999.getValue());
                    refundRepDTO.setTradeStatus(TradeStatusEnum.UNKNOW);
                }
            }else{
                throw new ResolveMsgException(SysRtnCodeEnum.R0002, TradeStatusEnum.PROCESS);
            }

        } catch (UnsupportedEncodingException e) {
            throw new ResolveMsgException(SysRtnCodeEnum.R0000, TradeStatusEnum.PROCESS);
        }
        return refundRepDTO;
    }


}
