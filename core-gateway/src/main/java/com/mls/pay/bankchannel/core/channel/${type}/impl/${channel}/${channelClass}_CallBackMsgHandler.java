package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel};

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.mls.pay.bankchannel.common.dto.${type}.*;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.common.exception.*;
import com.mls.pay.bankchannel.core.msg.impl.FreemarkChannelMsgHandlerImpl;
import com.mls.pay.bankchannel.core.utils.ChannelConfig;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_CallBackMsgHandler extends FreemarkChannelMsgHandlerImpl<CallBackReqDTO, CallBackRepDTO> {

    private static final Logger logger = LoggerFactory.getLogger(${channelClass}_CallBackMsgHandler.class);

    @Override
    protected String getTemplatePath() {
        return null;
    }

//    @Override
//    public CallBackReqDTO beforBuildMsg(CallBackReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {
//        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
//        try {
//
//            config.setPostUrl(config.getPayUri());
//            config.setCerPath(req.getChannelRemark().getCerPath());
//            config.setPxfPath(req.getChannelRemark().getPfxPath());
//            config.setMerchantId(req.getChannelRemark().getMerchantNo());
//            config.setPrivateKey(req.getChannelRemark().getZpk());
//
//        } catch (Exception e) {
//            throw new BuildMsgException(SysRtnCodeEnum.B0001,TradeStatusEnum.FAIL);
//        }
//        return super.beforBuildMsg(req, channelConfig);
//    }

    @Override
    public CallBackRepDTO afterCallBackMsg(CallBackReqDTO req, ChannelConfig channelConfig)  throws ResolveMsgException {
        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
        String privateKey = req.getChannelRemark().getZpk();
        CallBackRepDTO callBackRepDTO = new CallBackRepDTO();
        try {
            Map<String, Object> respMap = new HashMap<String, Object>();
            boolean flag = false;
//          boolean flag = ChinaPayUtils.verifyResponse(req.getCallBackContent(), respMap, config.getSignature(), config.getSignMethod(), config.getSignType(), privateKey, config.getCharset());
            if (flag) {
                String respCode = (String) respMap.get("respCode");
                String respMsg = (String) respMap.get("respMsg");
                String orderNumber = (String) respMap.get("orderNumber");
                String traceNumber = (String) respMap.get("traceNumber");
                String traceTime = (String) respMap.get("traceTime");
                String qid = (String) respMap.get("qid");
                String orderAmount = (String) respMap.get("orderAmount");
                String respTime = (String) respMap.get("respTime");
                if ("00".equals(respCode)) {
                // 交易处理成功
                    callBackRepDTO.setTradeStatus(TradeStatusEnum.SUCCESS);
                    callBackRepDTO.setRtnCode(BankRtnCodeEnum.S0000.name());
                    callBackRepDTO.setRtnMsg(BankRtnCodeEnum.S0000.getValue());
                } else if ("01".equals(respCode)) {
                    callBackRepDTO.setTradeStatus(TradeStatusEnum.PROCESS);
                    callBackRepDTO.setRtnCode(BankRtnCodeEnum.P0000.name());
                    callBackRepDTO.setRtnMsg(BankRtnCodeEnum.P0000.getValue());
                } else {
                    callBackRepDTO.setTradeStatus(TradeStatusEnum.UNKNOW);
                    callBackRepDTO.setRtnCode(BankRtnCodeEnum.S9999.name());
                    callBackRepDTO.setRtnMsg(BankRtnCodeEnum.S9999.getValue());
                }
            } else {
                throw new ResolveMsgException(SysRtnCodeEnum.R0002, TradeStatusEnum.UNKNOW);
            }
        } catch (Exception e) {
            throw new ResolveMsgException(SysRtnCodeEnum.R0000, TradeStatusEnum.UNKNOW);
        }
        return super.afterCallBackMsg(req, channelConfig);
    }

}
