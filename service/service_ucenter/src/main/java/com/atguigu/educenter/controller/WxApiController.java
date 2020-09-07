package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.util.ConstantPropertiesUtil;
import com.atguigu.educenter.util.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@RequestMapping("/api/ucenter/wx")
@Controller
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //获取扫描人信息，添加信息
    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session) {

        UcenterMember ucenterMember =null;
        try {
            /**
             * 1、获取code值，类似于验证码
             */
            //拿着code请求微信固定地址，得到accsess_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接3个参数：id、秘钥、code
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            /**
             *2、请求这个拼接号的地址，得到accsess_token和openid
             */
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //解析json字符串
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            //把扫描人信息添加到数据库中
            //判断数据库中是否存在扫描人信息，根据openid
           ucenterMember = memberService.getOpenId(openid);

           if (ucenterMember==null){//需要添加
               /**
                * 3、用第二步得到的2个值accsess_token和openid，再去请求一个地址得到微信扫描人信息
                */
               String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                       "?access_token=%s" +
                       "&openid=%s";
               //拼接参数
               String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
               //发送请求
               String resultUserInfo = HttpClientUtils.get(userInfoUrl);
               //获取用户信息
               HashMap mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
               String nickname = (String)mapUserInfo.get("nickname");//昵称
               String headimgurl = (String)mapUserInfo.get("headimgurl");//头像
               //向数据库中添加
               ucenterMember = new UcenterMember();
               ucenterMember.setOpenid(openid);
               ucenterMember.setNickname(nickname);
               ucenterMember.setAvatar(headimgurl);
               memberService.save(ucenterMember);
           }
        } catch (Exception e) {
            throw new GuLiException(20001,"获取用户信息失败");
        }
        //使用jwt根据member对象生成token字符串
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        //最后：返回首页面，通过token传递token字符串

        return "redirect:http://localhost:3000?token="+jwtToken;
    }

    //生成微信二维码
    @GetMapping("/login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl , %s相当于？代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuLiException(20001, e.getMessage());
        }
        //给占位符进行赋值
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL,
                "atguigu"
        );
        //重定向进行访问
        return "redirect:" + qrcodeUrl;
    }

}
