package com.station3.login.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@SuppressWarnings("serial")
@ToString(callSuper = true)
public class CustomUserDetailsDto extends LoginDto /*implements UserDetails*//*, Serializable*/ {

	private static final long serialVersionUID = 1L;

	private int enabled;

	//private String type;
	//private String loc;
	//private String msg;

	//USER_INFO 추가
	private String uidHide;

	private String deptName;
	private String userRoleName;

	//member
	private String compId;
	private String compName;
	//private String memId;
	private String memName;
	//private int subLevel;
	//private String comp;
	private String mobile;
	private String phone;
	//private int userPk;
//	private String cono1;
//	private String cono2;
//	private String cono3;

	//sub_member
	//private String companyQuery;
	private String companyResult;
	//private String companyRow;

	//Member_Level
	private String membership;

	private String[] works;

	private String snsId;
	private String snsType;
	private String snsEmail;
	private String snsEmailHide;
	private String collectedItems;
	private String accessToken;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private String indate;

	private Boolean isGuest;
	private Boolean isUser;
	private Boolean isMembershipUser;
	private Boolean isContractUser;
	private Boolean isSmartPinClubUser;

	private Boolean isExistPicture;

	/*private UserCompanyDeptResponseDto userCompanyDept;
	private List<UserCompanyManagerAuthFunctionResponseDto> authFunctionList;*/
	private String authType;
	private String position;

	private String authYn; //USER_INFO_AUTH.authYn

	/*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority(super.getUserRole())); //USER_ROLE

		*//*if(Objects.nonNull(this.authFunctionList) ) {
			//AUTH_FUNCTION
			for (UserCompanyManagerAuthFunctionResponseDto authFunction : this.authFunctionList) {
				Optional<String> function = Optional.ofNullable(authFunction.getAuthFunction());

				if (function.isPresent()) {
					authList.add(new SimpleGrantedAuthority(authFunction.getAuthFunction()));
				}
			}
		}*//*

		return authList;
	}*/

	/*@Override
	public String getPassword() { return upasswd; }

	@Override
	public String getUsername() {
		return uid;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled==1?true:false;
	}*/
}