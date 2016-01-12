package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel}.vld;

import com.mls.pay.bankchannel.common.dto.${type}.QueryPayReqDTO;
import com.mls.pay.bankchannel.common.enums.*;
import com.mls.pay.bankchannel.common.exception.VldException;
import com.mls.pay.bankchannel.core.validation.impl.BaseValidateImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}_QueryVld extends BaseValidateImpl<QueryPayReqDTO>{
    @Override
    public void validate(QueryPayReqDTO queryPayReqDTO) throws VldException {
        if(StringUtils.isBlank(queryPayReqDTO.getChannelRemark().getCerPath())){
            throw new VldException(SysRtnCodeEnum.V1024, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryPayReqDTO.getChannelRemark().getPfxPath())){
            throw new VldException(SysRtnCodeEnum.V1025, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryPayReqDTO.getChannelRemark().getZpk())){//zpk
            throw new VldException(SysRtnCodeEnum.V1026, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryPayReqDTO.getOriBankOrderNo())){
            throw new VldException(SysRtnCodeEnum.V1004, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(queryPayReqDTO.getBankNo())){
            throw new VldException(SysRtnCodeEnum.V1016, TradeStatusEnum.FAIL);
        }
    }
}
