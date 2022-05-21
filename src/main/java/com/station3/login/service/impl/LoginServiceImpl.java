/*
package com.station3.login.service.impl;

import com.castingn.login.dao.LoginDao;
import com.castingn.login.dto.CustomUserDetailsDto;
import com.castingn.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final HttpSession session;
    private final LoginDao loginDao;

    @Override
    public CustomUserDetailsDto getSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication != null && !authentication.getPrincipal().equals("anonymousUser") ){
            //return (CustomUserDetailsDto) authentication.getPrincipal();
            return (CustomUserDetailsDto) session.getAttribute("USER");
        } else {
        	return null;
        }

        //return authentication.getPrincipal().equals("anonymousUser") ? null : (CustomUserDetailsDto) authentication.getPrincipal();
    }

    @Override
    public ArrayList userInfoCheckMobile(String phone) {
        return loginDao.userInfoCheckMobile(phone);
    }

    @Override
    public int insertMobileCertificationCode(String mccUserKey, String mccCode, String mccExpiration) {
        return loginDao.insertMobileCertificationCode(mccUserKey, mccCode, mccExpiration);
    }

    @Override
    public Map selectMobileCertificationCode(String mccUserKey, String mccCode, String mccExpiration) {
        return loginDao.selectMobileCertificationCode(mccUserKey, mccCode, mccExpiration);
    }

    @Override
    public ArrayList<Map<String, String>> checkUserWithMobileAndEmail(String findMobile, String findEmail) {
        return loginDao.checkUserWithMobileAndEmail(findMobile, findEmail);
    }

    @Override
    public CustomUserDetailsDto findByUid(CustomUserDetailsDto customUserDetailsDto) {
        return loginDao.findByUid(customUserDetailsDto);
    }


}
*/
