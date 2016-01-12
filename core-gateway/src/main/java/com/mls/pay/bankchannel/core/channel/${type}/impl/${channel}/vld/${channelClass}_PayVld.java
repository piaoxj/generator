package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel}.vld;

import com.mls.pay.bankchannel.common.dto.${type}.PayReqDTO;
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
public class ${channelClass}_PayVld extends BaseValidateImpl<PayReqDTO>{
    @Override
    public void validate(PayReqDTO payReqDTO) throws VldException {
        if(StringUtils.isBlank(payReqDTO.getChannelRemark().getCerPath())){//cer
            throw new VldException(SysRtnCodeEnum.V1024, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payReqDTO.getChannelRemark().getPfxPath())){//pfx
            throw new VldException(SysRtnCodeEnum.V1025, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payReqDTO.getChannelRemark().getZpk())){//zpk
            throw new VldException(SysRtnCodeEnum.V1026, TradeStatusEnum.FAIL);
        }
        if(StringUtils.isBlank(payReqDTO.getBankOrderNo())){//payid
            throw new VldException(SysRtnCodeEnum.V1003, TradeStatusEnum.FAIL);
        }
        if(payReqDTO.getAmount()<=0L){//amount
            throw new VldException(SysRtnCodeEnum.V1009, TradeStatusEnum.FAIL);
        }
    }
}
