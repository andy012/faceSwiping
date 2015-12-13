package com.face.service.getui.impl;

import com.alibaba.fastjson.JSON;
import com.face.init.config.GetuiConfig;
import com.face.service.getui.Push2Single;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.em.EPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by andy on 12/11/15.
 */
@Service
public class Push2SingleImpl implements Push2Single{


    @Autowired
    private IGtPush iGtPush;


    @Override
    public IPushResult pushTransmissionTemplate(String clientId, Object object) {


        TransmissionTemplate template = null;
        try {
            List<Target> targets = new ArrayList<Target>();
            Target target1 = new Target();
            target1.setAppId(GetuiConfig.GE_TUI_APP_ID_VALUE);
            target1.setClientId(clientId);
            template = createTransmissionTemplate(JSON.toJSONString(object));
            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            message.setOfflineExpireTime(7 * 24 * 1000 * 3600);
            message.setData(template);
            IPushResult ret = iGtPush.pushMessageToSingle(message, target1);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    public static TransmissionTemplate createTransmissionTemplate(String content)
            throws Exception {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(GetuiConfig.GE_TUI_APP_ID_VALUE);
        template.setAppkey(GetuiConfig.GE_TUI_APP_KEY_VALUE);
        template.setTransmissionType(1);
        template.setTransmissionContent(content);
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
//		template.setPushInfo("", 1, "", "", "", "", "", "");

        //**********APN简单推送********//
       // APNPayload apnpayload = new APNPayload();
//		com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg alertMsg = new com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg(
//				"hahahaha");
//		apnpayload.setAlertMsg(alertMsg);
      //  apnpayload.setBadge(5);
//		apnpayload.setContentAvailable(1);
//		apnpayload.setCategory("ACTIONABLE");
      //  template.setAPNInfo(apnpayload);

        //************APN高级推送*******************//
//			APNPayload apnpayload = new APNPayload();
//			apnpayload.setBadge(4);
//			apnpayload.setSound("test2.wav");
//			apnpayload.setContentAvailable(1);
//			apnpayload.setCategory("ACTIONABLE");
//			APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
//			alertMsg.setBody("body");
//			alertMsg.setActionLocKey("ActionLockey");
//			alertMsg.setLocKey("LocKey");
//			alertMsg.addLocArg("loc-args");
//			alertMsg.setLaunchImage("launch-image");
//			// IOS8.2以上版本支持
//			alertMsg.setTitle("Title");
//			alertMsg.setTitleLocKey("TitleLocKey");
//			alertMsg.addTitleLocArg("TitleLocArg");
//
//			apnpayload.setAlertMsg(alertMsg);
//			template.setAPNInfo(apnpayload);


        return template;
    }

}
