package com.thylovezj.reader.controller;

import com.thylovezj.reader.entity.Member;
import com.thylovezj.reader.service.MemberService;
import com.thylovezj.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Resource
    public MemberService memberService;

    @GetMapping("/register.html")
    public ModelAndView showRegister() {
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
            memberService.createMember(username, password, nickname);
            res.put("code", "0");
            res.put("msg", "success");
        } catch (BussinessException e) {
            e.printStackTrace();
            res.put("code", e.getCode());
            res.put("msg", e.getMessage());
        }
        return res;
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
    }


    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session) {
        //获取当前请求中的验证码
        String kaptchaVerifyCode = (String) session.getAttribute("kaptchaVerifyCode");
        Map res = new HashMap();
        if (kaptchaVerifyCode == null || vc == null || !vc.equals(kaptchaVerifyCode)) {
            res.put("code", "VC01");
            res.put("msg", "验证码错误");
        } else {
            try {
                Member member = memberService.checkLogin(username, password);
                session.setAttribute("loginMember", member);
                res.put("code", 0);
                res.put("msg", 0);
            } catch (BussinessException e) {
                e.printStackTrace();
                res.put("code", e.getCode());
                res.put("msg", e.getMsg());
            }
        }
        return res;
    }

    @PostMapping("/update_read_state")
    @ResponseBody
    public Map update(Long memberId, Long bookId, Integer readState) {
        Map res = new HashMap();
        try {
            memberService.updateMemberReadState(memberId, bookId, readState);
            res.put("code", 0);
            res.put("msg", "success");
        } catch (BussinessException e) {
            e.printStackTrace();
            res.put("code", e.getCode());
            res.put("msg", e.getMsg());
        }
        return res;
    }
}
