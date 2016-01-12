package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel}.vld;

import com.mls.pay.bankchannel.common.dto.${type}.QueryPayReqDTO;
import com.mls.pay.bankchannel.common.dto.${type}.QueryRefundReqDTO;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.common.exception.VldException;
import com.mls.pay.bankchannel.core.validation.impl.BaseValidateImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_QueryRefundVld extends BaseValidateImpl<QueryRefundReqDTO>{
    @Override
    public void validate(QueryRefundReqDTO queryRefundReqDTO) throws VldException {
        if(StringUtils.isBlank(queryRefundReqDTO.getChannelRemark().getCerPath())){
            throw new VldException(SysRtnCodeEnum.V1024, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryRefundReqDTO.getChannelRemark().getPfxPath())){
            throw new VldException(SysRtnCodeEnum.V1025, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryRefundReqDTO.getChannelRemark().getZpk())){//zpk
            throw new VldException(SysRtnCodeEnum.V1026, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryRefundReqDTO.getOriBankOrderNo())){
            throw new VldException(SysRtnCodeEnum.V1104, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryRefundReqDTO.getBankNo())){
            throw new VldException(SysRtnCodeEnum.V1106, TradeStatusEnum.FAIL);
        }
    }
}
