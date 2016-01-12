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
public class ${channelClass}_QueryRefundMsgHandler extends FreemarkChannelMsgHandlerImpl<QueryRefundReqDTO,QueryRefundRepDTO>{

    private static final Logger logger=LoggerFactory.getLogger(${channelClass}_QueryRefundMsgHandler.class);

    @Override
    protected String getTemplatePath(){
        return"/${type}/${channel}/query_refund.ftl";
    }

    @Override
    public QueryRefundReqDTO beforBuildMsg(QueryRefundReqDTO req,ChannelConfig channelConfig)throws BuildMsgException{
        ${channelClass}_Config config=(${channelClass}_Config)channelConfig;

        try{
            req.setPostUrl(config.getQueryRefundUri());
            super.put("version", config.getVersion());
            super.put("charset", config.getCharset());
            super.put("merId", req.getChannelRemark().getMerchantNo());
            //super.put("transType", config.getRefTransType());// 交易类型
            super.put("orderTime", DateUtil.DateStampToStringMs(req.getCreateDateTime()));// 交易开始日期时间yyyyMMddHHmmss或yyyyMMdd
            super.put("orderNumber", req.getOriBankRefundNo());// 订单号
        }catch (Exception ex){
            throw new BuildMsgException(SysRtnCodeEnum.B0000, TradeStatusEnum.FAIL);
        }
        return req;
    }
//    @Override
//    public byte[] builderMsg(QueryRefundReqDTO req, ChannelConfig channelConfig) throws BuildMsgException {
//        ${channelClass}_Config config=(${channelClass}_Config)channelConfig;
//        Map<String, Object> map = new TreeMap<String, Object>();
//        map.putAll(super.getMap());
//        String result = ChinaPayUtils.buildMsg(map, config.getPostUrl(), config.getSignType(), config.getSignature(), config.getSignMethod(), config.getPrivateKey(), config.getCharset());
//        return result.getBytes();
//    }
    @Override
    public QueryRefundRepDTO resolveMsg(QueryRefundReqDTO req,byte[]rtnMsg,ChannelConfig channelConfig)throws ResolveMsgException{

        ${channelClass}_Config config=(${channelClass}_Config)channelConfig;
        String privateKey = req.getChannelRemark().getZpk();
        QueryRefundRepDTO queryRefundRepDTO=new QueryRefundRepDTO();
        String repString=null;
        try{
            repString=StringUtils.toString(rtnMsg,config.getCharset());
            Map<String, Object> respMap = new HashMap<String, Object>();
            boolean flag = false;
    //      boolean flag = ChinaPayUtils.verifyResponse(repString,respMap,config.getSignature(),config.getSignMethod(),config.getSignType(),privateKey,config.getCharset());
            if(flag){
                String queryResult = (String)respMap.get("queryResult");
                String respCode = (String)respMap.get("respCode");
                String respMsg = (String)respMap.get("respMsg");
                String orderNumber = (String)super.get("orderNumber");
                String qid =(String)respMap.get("qid");
                if("00".equals(respCode)){
                    if("0".equals(queryResult)){
                        String settleAmount = (String)respMap.get("settleAmount");
                        if(StringUtils.isNotBlank(settleAmount)){
                            queryRefundRepDTO.setAmount(new Long(settleAmount));
                        }
                        queryRefundRepDTO.setRtnCode(BankRtnCodeEnum.S0000.name());
                        queryRefundRepDTO.setRtnMsg(BankRtnCodeEnum.S0000.getValue());
                        queryRefundRepDTO.setTradeStatus(TradeStatusEnum.SUCCESS);
                        queryRefundRepDTO.setOriBankOrderNo(orderNumber);
                        queryRefundRepDTO.setBankNo(qid);
                    }else if("2".equals(queryResult)){
                        queryRefundRepDTO.setRtnCode(BankRtnCodeEnum.P0000.name());
                        queryRefundRepDTO.setRtnMsg(BankRtnCodeEnum.P0000.getValue());
                        queryRefundRepDTO.setTradeStatus(TradeStatusEnum.PROCESS);
                    }
                }else {
                    if("1".equals(queryResult)){
                        queryRefundRepDTO.setRtnCode(BankRtnCodeEnum.F0000.name());
                        queryRefundRepDTO.setRtnMsg(BankRtnCodeEnum.F0000.getValue());
                        queryRefundRepDTO.setTradeStatus(TradeStatusEnum.FAIL);
                    }else if("3".equals(queryResult)){
                        queryRefundRepDTO.setRtnCode(BankRtnCodeEnum.F0002.name());
                        queryRefundRepDTO.setRtnMsg(BankRtnCodeEnum.F0002.getValue());
                        queryRefundRepDTO.setTradeStatus(TradeStatusEnum.FAIL);
                    }else{
                        queryRefundRepDTO.setRtnCode(BankRtnCodeEnum.S9999.name());
                        queryRefundRepDTO.setRtnMsg(BankRtnCodeEnum.S9999.getValue());
                        queryRefundRepDTO.setTradeStatus(TradeStatusEnum.UNKNOW);
                    }
                }

            }else{
                throw new ResolveMsgException(SysRtnCodeEnum.R0002, TradeStatusEnum.PROCESS);
            }
        }catch(UnsupportedEncodingException e){
                throw new ResolveMsgException(SysRtnCodeEnum.R0000, TradeStatusEnum.PROCESS);
        }
        return queryRefundRepDTO;

    }
}
