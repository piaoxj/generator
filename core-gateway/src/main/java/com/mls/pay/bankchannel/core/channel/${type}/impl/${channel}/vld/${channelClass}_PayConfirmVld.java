package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel}.vld;

import com.mls.pay.bankchannel.common.dto.${type}.payConfirmReqDTO;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.common.exception.VldException;
import com.mls.pay.bankchannel.common.util.CurrencyUtil;
import com.mls.pay.bankchannel.common.util.DateUtil;
import com.mls.pay.bankchannel.core.validation.impl.BaseValidateImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_PayConfirmVld extends BaseValidateImpl<payConfirmReqDTO>{
    @Override
    public void validate(PayConfirmReqDTO payConfirmReqDTO) throws VldException {
        if(StringUtils.isBlank(payConfirmReqDTO.getChannelRemark().getCerPath())){
            throw new VldException(SysRtnCodeEnum.V1024, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payConfirmReqDTO.getChannelRemark().getPfxPath())){
            throw new VldException(SysRtnCodeEnum.V1025, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payConfirmReqDTO.getCardNo())){
            throw new VldException(SysRtnCodeEnum.V1005, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payConfirmReqDTO.getCerCode())){
            throw new VldException(SysRtnCodeEnum.V1010, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payConfirmReqDTO.getBankOrderNo())){
            throw new VldException(SysRtnCodeEnum.V1004, TradeStatusEnum.FAIL);
        }

        if(StringUtils.isBlank(payConfirmReqDTO.getUserId())){//userId
            throw new VldException(SysRtnCodeEnum.V1007, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payConfirmReqDTO.getHolderName())){
            throw new VldException(SysRtnCodeEnum.V1008, TradeStatusEnum.FAIL);
        }
        if(payConfirmReqDTO.getCerType()==null){
            throw new VldException(SysRtnCodeEnum.V1011, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payConfirmReqDTO.getVerifyCode())){
            throw new VldException(SysRtnCodeEnum.V1014, TradeStatusEnum.FAIL);
        }

        if(StringUtils.isBlank(payConfirmReqDTO.getMobileNo())){
            throw new VldException(SysRtnCodeEnum.V1015, TradeStatusEnum.FAIL);
        }
        if(payConfirmReqDTO.getAmount()<=0L){
            throw new VldException(SysRtnCodeEnum.V1009, TradeStatusEnum.FAIL);
        }

        if(payConfirmReqDTO.getCardType()==null){
            throw new VldException(SysRtnCodeEnum.V1006, TradeStatusEnum.FAIL);
        }
        if(payConfirmReqDTO.getCardType().equals(CardTypeEnum.CREDIT)){
            if(StringUtils.isBlank(payConfirmReqDTO.getExpireDate())){
                throw new VldException(SysRtnCodeEnum.V1012, TradeStatusEnum.FAIL);
            }
            if(StringUtils.isBlank(payConfirmReqDTO.getCvv2())){//cvv2
                throw new VldException(SysRtnCodeEnum.V1013, TradeStatusEnum.FAIL);
            }
        }
    }
}
