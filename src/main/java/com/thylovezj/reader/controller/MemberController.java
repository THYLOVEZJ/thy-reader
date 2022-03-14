package com.thylovezj.reader.controller;

import com.thylovezj.reader.service.MemberService;
import com.thylovezj.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Resource
    public MemberService memberService;

    @GetMapping("/register.html")
    public ModelAndView showReigster() {
        return new ModelAndView("/register");
    }

    @PostMapping("/registe")
    @ResponseBody
    public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request) {
        //获取当前请求中的验证码
        String kaptchaVerifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        Map res = new HashMap();
        if (kaptchaVerifyCode == null || vc == null || !vc.equals(kaptchaVerifyCode)) {
            res.put("code", "VC01");
            res.put("msg", "验证码错误");
        } else {
            res.put("code", "0");
            res.put("msg", "success");
        }
        try {
            memberService.createMember(username,password,nickname);
            res.put("code","0");
            res.put("msg","success");
        } catch (BussinessException e) {
            e.printStackTrace();
            res.put("code",e.getCode());
            res.put("msg",e.getMessage());
        }
        return res;
    }
}
