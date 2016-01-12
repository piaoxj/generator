package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel};

import com.mls.pay.bankchannel.common.dto.${type}.*;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.core.channel.ChannelActionProcess;
import com.mls.pay.bankchannel.core.channel.${type}.Abs${typeClass}PayChannel;
import com.mls.pay.bankchannel.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_ChannelImpl extends Abs${typeClass}PayChannel {

    private static final Logger logger = LoggerFactory.getLogger(${channelClass}_ChannelImpl.class);

    @Override
    public PayRepDTO pay(PayReqDTO payReqDTO) throws VldException, BuildMsgException, ResolveMsgException, CommuException {
        ChannelActionProcess<PayReqDTO, PayRepDTO> channelActionProcess = new ChannelActionProcess<PayReqDTO, PayRepDTO>();
        channelActionProcess.setChannelValidate(super.getChannelValidateHandler().get(TradeTypeEnum.PAY));
        channelActionProcess.setChannelTongXinHandler(super.getChannelTongXinHandler());
        channelActionProcess.setChannelMsgHandler(super.getChannelMsgHandler().get(TradeTypeEnum.PAY));
        channelActionProcess.setChannelConfig(super.getChannelConfig());
        PayRepDTO payRepDTO = channelActionProcess.doProcess(payReqDTO);
        return payRepDTO;
    }


    @Override
    public QueryPayRepDTO queryPay(QueryPayReqDTO queryPayReqDTO) throws VldException, BuildMsgException, ResolveMsgException, CommuException {
        ChannelActionProcess<QueryPayReqDTO, QueryPayRepDTO> channelActionProcess = new ChannelActionProcess<QueryPayReqDTO, QueryPayRepDTO>();
        channelActionProcess.setChannelValidate(super.getChannelValidateHandler().get(TradeTypeEnum.QUERY));
        channelActionProcess.setChannelTongXinHandler(super.getChannelTongXinHandler());
        channelActionProcess.setChannelMsgHandler(super.getChannelMsgHandler().get(TradeTypeEnum.QUERY));
        channelActionProcess.setChannelConfig(super.getChannelConfig());
        QueryPayRepDTO queryPayRepDTO = channelActionProcess.doProcess(queryPayReqDTO);
        return queryPayRepDTO;
    }

    @Override
    public RefundRepDTO refund(RefundReqDTO refundReqDTO) throws VldException, BuildMsgException, ResolveMsgException, CommuException {
        ChannelActionProcess<RefundReqDTO, RefundRepDTO> channelActionProcess = new ChannelActionProcess<RefundReqDTO, RefundRepDTO>();
        channelActionProcess.setChannelValidate(super.getChannelValidateHandler().get(TradeTypeEnum.REFUND));
        channelActionProcess.setChannelTongXinHandler(super.getChannelTongXinHandler());
        channelActionProcess.setChannelMsgHandler(super.getChannelMsgHandler().get(TradeTypeEnum.REFUND));
        channelActionProcess.setChannelConfig(super.getChannelConfig());
        RefundRepDTO refundRepDTO = channelActionProcess.doProcess(refundReqDTO);
        return refundRepDTO;
    }

    @Override
    public QueryRefundRepDTO queryRefund(QueryRefundReqDTO queryRefundReqDTO) throws VldException, BuildMsgException, ResolveMsgException, CommuException {
        ChannelActionProcess<QueryRefundReqDTO, QueryRefundRepDTO> channelActionProcess = new ChannelActionProcess<QueryRefundReqDTO, QueryRefundRepDTO>();
        channelActionProcess.setChannelValidate(super.getChannelValidateHandler().get(TradeTypeEnum.QUERYREFUND));
        channelActionProcess.setChannelTongXinHandler(super.getChannelTongXinHandler());
        channelActionProcess.setChannelMsgHandler(super.getChannelMsgHandler().get(TradeTypeEnum.QUERYREFUND));
        channelActionProcess.setChannelConfig(super.getChannelConfig());
        QueryRefundRepDTO queryRefundRepDTO = channelActionProcess.doProcess(queryRefundReqDTO);
        return queryRefundRepDTO;
    }

//    @Override
//    public CheckFileRepDTO checkFile(CheckFileReqDTO checkFileReqDTO) throws VldException, BuildMsgException, ResolveMsgException, CommuException {
//        ChannelActionProcess<CheckFileReqDTO, CheckFileRepDTO> channelActionProcess = new ChannelActionProcess<CheckFileReqDTO, CheckFileRepDTO>();
//        channelActionProcess.setChannelValidate(super.getChannelValidateHandler().get(TradeTypeEnum.CHECKFILE));
//        channelActionProcess.setChannelTongXinHandler(super.getChannelTongXinHandler());
//        channelActionProcess.setChannelMsgHandler(super.getChannelMsgHandler().get(TradeTypeEnum.CHECKFILE));
//        channelActionProcess.setChannelConfig(super.getChannelConfig());
//        CheckFileRepDTO checkFileRepDTO = channelActionProcess.doProcess(checkFileReqDTO);
//        return checkFileRepDTO;
//    }

    @Override
    public CallBackRepDTO callback(CallBackReqDTO callBackReqDTO) throws VldException, BuildMsgException, ResolveMsgException, CommuException {
        ChannelActionProcess<CallBackReqDTO, CallBackRepDTO> channelActionProcess = new ChannelActionProcess<CallBackReqDTO, CallBackRepDTO>();
        channelActionProcess.setChannelValidate(super.getChannelValidateHandler().get(TradeTypeEnum.CALLBACK));
        channelActionProcess.setChannelConfig(super.getChannelConfig());
        channelActionProcess.setChannelMsgHandler(super.getChannelMsgHandler().get(TradeTypeEnum.CALLBACK));
        CallBackRepDTO callBackRepDTO = channelActionProcess.doProcessCallback(callBackReqDTO);
        return callBackRepDTO;
        }

    @Override
    public RefundCallBackRepDTO refundCallback(RefundCallBackReqDTO refundCallBackReqDTO) throws BuildMsgException, ResolveMsgException, CommuException {
        ChannelActionProcess<RefundCallBackReqDTO, RefundCallBackRepDTO> channelActionProcess = new ChannelActionProcess<RefundCallBackReqDTO, RefundCallBackRepDTO>();
        channelActionProcess.setChannelValidate(super.getChannelValidateHandler().get(TradeTypeEnum.REFUNDCALLBACK));
        channelActionProcess.setChannelConfig(super.getChannelConfig());
        channelActionProcess.setChannelMsgHandler(super.getChannelMsgHandler().get(TradeTypeEnum.REFUNDCALLBACK));
        RefundCallBackRepDTO refundCallBackRepDTO = channelActionProcess.doProcessCallback(refundCallBackReqDTO);
        return refundCallBackRepDTO;
        }

}
