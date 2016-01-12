package com.mls.pay.bankchannel.core.${type};

import com.mls.pay.bankchannel.common.${typeClass}PayFacade;
import com.mls.pay.bankchannel.common.dto.${type}.*;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.common.util.*;
import com.mls.pay.bankchannel.core.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class Test${channelClass}Router extends BaseTest {

    @Resource
    private ${typeClass}PayFacade ${type}PayFacade;

    @Test
    public void pay() {
        PayReqDTO payReqDTO = new PayReqDTO();
        payReqDTO.setBizOrderNo("biz00001");
        payReqDTO.setBizCode("MEIMIAO");
        payReqDTO.setChannelCode("QP_BILLPAY_CD");

        payReqDTO.setAmount(100L);
        payReqDTO.setCardType(CardTypeEnum.CREDIT);
        /******************************/
        payReqDTO.setUserId("119999");
        payReqDTO.setCardNo("4380880000000007");
        payReqDTO.setCerType(CerTypeEnum.ID);
        payReqDTO.setCerCode("32058219870706111X");
        payReqDTO.setHolderName("test");
        payReqDTO.setMobileNo("15901283289");
        payReqDTO.setExpireDate("0911");
        payReqDTO.setCvv2("111");


        PayRepDTO payRepDTO = ${type}PayFacade.pay(payReqDTO);
        System.out.println("**********" + payRepDTO.toString());
    }

//    @Test
//    public void payConfirm() {
//        PayConfirmReqDTO payConfirmReqDTO = new PayConfirmReqDTO();
//        payConfirmReqDTO.setBizOrderNo("biz00001");
//        payConfirmReqDTO.setAmount(100L);
//        payConfirmReqDTO.setBizCode("MEIMIAO");
//        payConfirmReqDTO.setChannelCode("QP_BILLPAY_CD");
//        payConfirmReqDTO.setCardType(CardTypeEnum.CRDE);
//        /******************************/
//        payConfirmReqDTO.setOriBankOrderNo("BILLPAY1421574649356");
//        payConfirmReqDTO.getMap().put(EnumMapKey.BILLPAY_TOKEN.name(), "961267");
//        payConfirmReqDTO.setVerifyCode("");
//        /******************************/
//        payConfirmReqDTO.setUserId("119999");
//        payConfirmReqDTO.setCardNo("4380880000000007");
//        payConfirmReqDTO.setCerType(CerTypeEnum.ID);
//        payConfirmReqDTO.setCerCode("32058219870706111X");
//        payConfirmReqDTO.setHolderName("test");
//        payConfirmReqDTO.setMobileNo("15901283289");
//        payConfirmReqDTO.setExpireDate("0911");
//        payConfirmReqDTO.setCvv2("111");
//
//
//        PayConfirmRepDTO payConfirmRepDTO = ${type}PayFacade.payConfirm(payConfirmReqDTO);
//        System.out.println("**********" + payConfirmRepDTO.toString());
//    }

//    @Test
//    public void cardInfo() {
//        QueryCardInfoReqDTO queryCardInfoReq = new QueryCardInfoReqDTO();
//        queryCardInfoReq.setBizCode("MEIMIAO");
//        queryCardInfoReq.setCardNo("4380880000000007");
//        QueryCardInfoRepDTO payRepDTO = ${type}PayFacade.queryCardInfo(queryCardInfoReq);
//        System.out.println("**********" + payRepDTO.toString());
//    }

    @Test
    public void queryPay() {
        QueryPayReqDTO queryPayReqDTO = new QueryPayReqDTO();
        queryPayReqDTO.setBizCode("MEIMIAO");
        queryPayReqDTO.setOriBankOrderNo("BILLPAY1421574649356");
        queryPayReqDTO.setBankNo("000009385950");
        QueryPayRepDTO queryPayRepDTO = ${type}PayFacade.queryPay(queryPayReqDTO);
        System.out.println("**********" + queryPayRepDTO.toString());
    }

    @Test
    public void queryRefund() {
        QueryRefundReqDTO queryRefundReqDTO = new QueryRefundReqDTO();
        queryRefundReqDTO.setBizCode("MEIMIAO");
        queryRefundReqDTO.setOriBankOrderNo("REFUND1421575239950");
        queryRefundReqDTO.setBankNo("000009385951");
        QueryRefundRepDTO queryRefundRepDTO = ${type}PayFacade.queryRefund(queryRefundReqDTO);
        System.out.println("**********" + queryRefundRepDTO.toString());
    }

    @Test
    public void refund() {
        RefundReqDTO refundReqDTO = new RefundReqDTO();
        refundReqDTO.setAmount(1L);
//        refundReqDTO.setChannelCode("QP_BILLPAY_CD");
        refundReqDTO.setBizCode("MEIMIAO");
        refundReqDTO.setFinshDateTime(DateUtil.convertStr2DateForQuery("20150118174529"));
        refundReqDTO.setBankNo("000009385950");
        RefundRepDTO refundRepDTO = ${type}PayFacade.refund(refundReqDTO);
        System.out.println("**********" + refundRepDTO.toString());
    }
}
