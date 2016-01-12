package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel}.vld;

import com.mls.pay.bankchannel.common.dto.${type}.SignReqDTO;
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
public class ${channelClass}_SignVld extends BaseValidateImpl<SignReqDTO>{

    @Override
    public void validate(SignReqDTO signReqDTO) throws VldException {
        if(StringUtils.isBlank(signReqDTO.getChannelRemark().getCerPath())){
            throw new VldException(SysRtnCodeEnum.V1024, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(signReqDTO.getChannelRemark().getPfxPath())){
            throw new VldException(SysRtnCodeEnum.V1025, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(signReqDTO.getCardNo())){
            throw new VldException(SysRtnCodeEnum.V1005, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(signReqDTO.getCerCode())){
            throw new VldException(SysRtnCodeEnum.V1010, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(signReqDTO.getBankSignNo())){
            throw new VldException(SysRtnCodeEnum.V1003, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(signReqDTO.getUserId())){//userId
            throw new VldException(SysRtnCodeEnum.V1007, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(signReqDTO.getHolderName())){
            throw new VldException(SysRtnCodeEnum.V1008, TradeStatusEnum.FAIL);
        }
        if(signReqDTO.getCerType()==null){
            throw new VldException(SysRtnCodeEnum.V1011, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(signReqDTO.getMobileNo())){
            throw new VldException(SysRtnCodeEnum.V1015, TradeStatusEnum.FAIL);
        }
        if(signReqDTO.getAmount()<=0L){
            throw new VldException(SysRtnCodeEnum.V1009, TradeStatusEnum.FAIL);
        }
        if(signReqDTO.getCardType()==null){
            throw new VldException(SysRtnCodeEnum.V1006, TradeStatusEnum.FAIL);
        }
        if(signReqDTO.getCardType().equals(CardTypeEnum.CREDIT)){
            if(StringUtils.isBlank(signReqDTO.getExpireDate())){
                throw new VldException(SysRtnCodeEnum.V1012, TradeStatusEnum.FAIL);
            }
            if(StringUtils.isBlank(signReqDTO.getCvv2())){
                throw new VldException(SysRtnCodeEnum.V1013, TradeStatusEnum.FAIL);
            }
        }
    }
}
