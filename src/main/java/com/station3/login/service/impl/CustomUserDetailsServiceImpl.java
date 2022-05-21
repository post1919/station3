/*
package com.station3.login.service.impl;

import com.castingn.common.ConstantProperties;
import com.castingn.login.dao.UserDao;
import com.castingn.login.dto.CustomUserDetailsDto;
import com.castingn.mypage.dto.UserCompanyManagerAuthFunctionResponseDto;
import com.castingn.mypage.dto.UserCompanyManagerAuthResponseDto;
import com.castingn.user.UserRole;
import com.castingn.user.dto.MemberLoginLogDto;
import com.castingn.user.service.impl.UserServiceImpl;
import com.castingn.util.CommonUtils;
import com.station3.login.dto.CustomUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	private final UserDao userDao;
	private final UserServiceImpl userService;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

		//회원정보 조회
		CustomUserDetailsDto user = userDao.getUserById(memberId, null);

		//UserDetailsService 인터페이스를 구현한 클래스의 loadUserByUsername() 메서드
		if( Objects.isNull(user) ) {
			//throw new UsernameNotFoundException("memberId " + memberId + " not found");
			throw new InternalAuthenticationServiceException(memberId);
		}

		this.additionalUserInfo(user); //회원 부가정보 설정

		return user;
	}

	public void additionalUserInfo(CustomUserDetailsDto user){
		//관리자 권한
		UserCompanyManagerAuthResponseDto userCompanyAuth = userDao.getUserCompanyManagerAuth(user.getUid());
		if( Objects.nonNull(userCompanyAuth) ){
			user.setAuthType(userCompanyAuth.getAuthType());
		}

		//관리자 권한 목록
		List<UserCompanyManagerAuthFunctionResponseDto> userCompanyAuthFunctionList = userDao.getUserCompanyManagerAuthFunctionList(user.getUid());
		if( Objects.nonNull(userCompanyAuthFunctionList) ){
			user.setAuthFunctionList(userCompanyAuthFunctionList);
		}

		//int insertMemberLoginLogResult = this.insertMemberLoginLog(user);

		//회원 등급관련 설정
		user = userService.setUserRole(user);
		user.setUserRoleName(UserRole.getByCode(user.getUserRole()).getDesc()); //회원등급명

		//회원사진 존재여부 설정
		String upicture = user.getUpicture();
		try {
			Boolean isExistPicture = CommonUtils.isExistFile(ConstantProperties.PATH_PROFILE_PICTURE + upicture);
			user.setIsExistPicture(isExistPicture);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public int insertMemberLoginLog(CustomUserDetailsDto user) {
		MemberLoginLogDto memberLoginLogDto = new MemberLoginLogDto();
			*/
/*
			php부분
				LOGIN_ID = '{$LoginID}',
				LOGIN_NAME = '{$mem_name}',
				LOGIN_IP = '{$_SERVER['REMOTE_ADDR']}',
				LOGIN_DATETIME = NOW(),
				LOGIN_TIMESTAMP = '{$cur_timestamp}',
				LOGIN_PATH = '{$_SERVER['HTTP_REFERER']}',
				LOGIN_AGENT = '{$_SERVER['HTTP_USER_AGENT']}',
				LOGIN_MOBILE = '{$visit_mobile}'
			 *//*


			*/
/*$visit_mobile = "N";
			if($_SESSION['browser_type'] === "PC") {
				$visit_mobile = "N";
			} else {
				$visit_mobile = "Y";
			}

			memberLoginLogDto.setLoginId(memberId);
			memberLoginLogDto.setLoginName(user.getName());
			memberLoginLogDto.setLoginIp($_SERVER['REMOTE_ADDR']);
			memberLoginLogDto.setLoginDatetime();
			memberLoginLogDto.setLoginTimestamp(time());
			memberLoginLogDto.setLoginPath($_SERVER['HTTP_REFERER']);
			memberLoginLogDto.setLoginAgent($_SERVER['HTTP_USER_AGENT']);
			memberLoginLogDto.setLoginMobile($visit_mobile);
			 *//*

			return userDao.insertMemberLoginLog(memberLoginLogDto);
	}
}

*/
