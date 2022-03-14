package com.thylovezj.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thylovezj.reader.entity.Member;
import com.thylovezj.reader.mapper.MemberMapper;
import com.thylovezj.reader.service.MemberService;
import com.thylovezj.reader.service.exception.BussinessException;
import com.thylovezj.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("MemberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    /***
     * 会员注册，创建新会员
     * @param username 会员名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        List<Member> members = memberMapper.selectList(queryWrapper);
//        如果用户数量大于0
        if (members.size()>0){
            throw new BussinessException("M01","用户名已存在");
        }

        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt = new Random().nextInt(1000)+1000;
        String md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return null;
    }
}
