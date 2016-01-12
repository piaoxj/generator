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
public class ${channelClass}_PayMsgHandler extends FreemarkChannelMsgHandlerImpl<PayReqDTO, PayRepDTO> {

    private static final Logger logger = LoggerFactory.getLogger(${channelClass}_PayMsgHandler.class);

    @Override
    protected String getTemplatePath() {
        return "/${type}/${channel}/pay.ftl";
    }

    @Override
    public PayReqDTO beforBuildMsg(PayReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {
        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
        try {

            config.setPxfPath(req.getChannelRemark().getPfxPath());

        } catch (Exception e) {
            throw new BuildMsgException(SysRtnCodeEnum.B0001, TradeStatusEnum.FAIL);
        }
        try{
            req.setPostUrl(config.getPayUri());
            super.put("version", config.getVersion());
            super.put("charset", config.getCharset());
            super.put("merId", req.getChannelRemark().getMerchantNo());// 商户代码
        }catch (Exception ex){
            throw new BuildMsgException(SysRtnCodeEnum.B0000, TradeStatusEnum.FAIL);
        }
        return req;
    }
//    @Override
//    public byte[] builderMsg(PayReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {
//        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
//        String privateKey = req.getChannelRemark().getZpk();
//        Map<String, Object> map = new TreeMap<String, Object>();
//        map.putAll(super.getMap());
//        String result = ChinaPayUtils.buildMsg(map, req.getPostUrl(), config.getSignType(), config.getSignature(), config.getSignMethod(), privateKey, config.getCharset());
//        return result.getBytes();
//        }

    @Override
    public PayRepDTO resolveMsg(PayReqDTO req,byte[] rtnMsg, ChannelConfig channelConfig) throws ResolveMsgException {
        ${channelClass}_Config config = (${channelClass}_Config) channelConfig;
        String privateKey = req.getChannelRemark().getZpk();
        PayRepDTO payRepDTO = new PayRepDTO();
        String repString = null;
        try {
        repString = StringUtils.toString(rtnMsg, config.getCharset());
        if (repString != null && !"".equals(repString)) {
            payRepDTO.setRtnCode(BankRtnCodeEnum.S0000.name());
            payRepDTO.setRtnMsg(BankRtnCodeEnum.S0000.getValue());
            payRepDTO.setTradeStatus(TradeStatusEnum.SUCCESS);
        } else {
            payRepDTO.setRtnCode(BankRtnCodeEnum.F0000.name());
            payRepDTO.setRtnMsg(BankRtnCodeEnum.F0000.getValue());
            payRepDTO.setTradeStatus(TradeStatusEnum.FAIL);
        }
            payRepDTO.setRepContent(repString);
        } catch (UnsupportedEncodingException e) {
        throw new ResolveMsgException(SysRtnCodeEnum.R0000, TradeStatusEnum.PROCESS);
        }
        return payRepDTO;
    }
}
