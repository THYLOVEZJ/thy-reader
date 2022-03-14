package com.thylovezj.reader.service;

import com.thylovezj.reader.entity.Member;

public interface MemberService {
    /***
     * 会员注册，创建新会员
     * @param username 会员名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname);
}
