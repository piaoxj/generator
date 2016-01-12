package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel}.vld;

import com.mls.pay.bankchannel.common.dto.${type}.RefundReqDTO;
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
public class ${channelClass}_RefundVld extends BaseValidateImpl<RefundReqDTO>{
    @Override
    public void validate(RefundReqDTO refundReqDTO) throws VldException {
        if(StringUtils.isBlank(refundReqDTO.getChannelRemark().getCerPath())){
            throw new VldException(SysRtnCodeEnum.V1024, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(refundReqDTO.getChannelRemark().getPfxPath())){
            throw new VldException(SysRtnCodeEnum.V1025, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(refundReqDTO.getChannelRemark().getZpk())){//zpk
            throw new VldException(SysRtnCodeEnum.V1026, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(refundReqDTO.getBankRefundNo())){
            throw new VldException(SysRtnCodeEnum.V1103, TradeStatusEnum.FAIL);
        }
//        if(StringUtils.isBlank(refundReqDTO.getBankNo())){
//            throw new VldException(SysRtnCodeEnum.V1106, TradeStatusEnum.FAIL);
//        }
        if(refundReqDTO.getAmount()<=0){
            throw new VldException(SysRtnCodeEnum.V1107, TradeStatusEnum.FAIL);
        }
        if(refundReqDTO.getFinshDateTime()==null){
            throw new VldException(SysRtnCodeEnum.V1108, TradeStatusEnum.FAIL);
        }
    }
}
