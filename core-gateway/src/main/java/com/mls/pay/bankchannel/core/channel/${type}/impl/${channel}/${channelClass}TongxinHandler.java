package com.mls.pay.bankchannel.core.channel.${type}.impl.${channel};
import com.mls.pay.bankchannel.common.enums.SysRtnCodeEnum;
import com.mls.pay.bankchannel.common.exception.CommuException;
import com.mls.pay.bankchannel.core.tongxin.ChannelTongXinHandler;
import com.mls.pay.bankchannel.core.utils.ChannelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author piaoxj
 * @email piaoxj89@gmail.com
 * @mobile 15901283289
 */
public class ${channelClass}TongxinHandler<REQ extends BaseReq> implements ChannelTongXinHandler{

   private static final Logger logger = LoggerFactory.getLogger(${channelClass}TongxinHandler.class);

   @Override
   public byte[] send(BaseReq req,byte[] reqMsg, ChannelConfig channelConfig) throws CommuException {
       return new byte[0];
   }
}