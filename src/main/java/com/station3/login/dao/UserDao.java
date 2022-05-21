package com.station3.login.dao;
/*

import com.castingn.alrim.dto.AlrimRequestDto;
import com.castingn.alrim.dto.AlrimResponseDto;
import com.castingn.common.dto.*;
import com.castingn.login.dto.CustomUserDetailsDto;
import com.castingn.login.dto.LoginDto;
import com.castingn.login.dto.UserInfoRequestDto;
import com.castingn.login.mapper.UserMapper;
import com.castingn.mypage.dto.*;
import com.castingn.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {
	private final UserMapper userMapper;

	public CustomUserDetailsDto getUserById(String memberId, String snsType) {
		return userMapper.getUserById(memberId, snsType);
	}

	public CustomUserDetailsDto getUserByIdSelectOne(String memberId) {

		CustomUserDetailsDto user = userMapper.getUserByIdSelectOne(memberId);

		return user;
	}

	public List<CustomUserDetailsDto> getUserByName(String memberId) {
		List<CustomUserDetailsDto> user = userMapper.getUserByName(memberId);
		return user;
	}

	public int insertMemberLoginLog(MemberLoginLogDto memberLoginLogDto) {
		return userMapper.insertMemberLoginLog(memberLoginLogDto);
	}

	@Cacheable(value="plazaDefaultCache", cacheManager = "ehCacheManager")
	public PlazaDefaultDto getPlazaDefault() {
		return userMapper.getPlazaDefault();
	}

	//@Cacheable(value="getMemberLevelCache", key="#memberLevelDto", cacheManager = "ehCacheManager")
	public List<MemberLevelDto> getMemberLevel(MemberLevelDto memberLevelDto) {
		return userMapper.getMemberLevel(memberLevelDto);
	}

	@Cacheable(value="getBankListCache", cacheManager = "ehCacheManager")
	public BankListDto getBankList() {
		return userMapper.getBankList();
	}

	public int insertMember(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.insertMember(customUserDetailsDto);
	}

	public int insertUserInfo(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.insertUserInfo(customUserDetailsDto);
	}

	public int insertPlazaPoint(PlazaPointDto plazaPointDto) {
		return userMapper.insertPlazaPoint(plazaPointDto);
	}

	public int insertMemberRecommand(MemberRecommandDto memberRecommandDto) {
		return userMapper.insertMemberRecommand(memberRecommandDto);
	}

	public int memberLevelUpdate(LoginDto loginDto) {
		return userMapper.memberLevelUpdate(loginDto);
	}

	public int insertMembershipChangeLog(MembershipChangeLogDto membershipChangeLogDto) {
		return userMapper.insertMembershipChangeLog(membershipChangeLogDto);
	}

	public MembershipChangeLogDto getMembershipChangeLog(String uid) {
		return userMapper.getMembershipChangeLog(uid);
	}

	@Cacheable(value="getCouponPub", key="#compId", cacheManager = "ehCacheManager")
    public int getCouponPub(String compId) {
		return userMapper.getCouponPub(compId);
    }

	@Cacheable(value="getPlazaPoint", key="#compId", cacheManager = "ehCacheManager")
	public int getPlazaPoint(String compId) {
		return userMapper.getPlazaPoint(compId);
	}

	@Cacheable(value="myalrimListCount", cacheManager = "ehCacheManager")
	public int myalrimListCount(AlrimRequestDto myalrimRequestDto){
		return userMapper.myalrimListCount(myalrimRequestDto);
	}

	@Cacheable(value="getPlazaCartCount", cacheManager = "ehCacheManager")
	public int getPlazaCartCount(String memberId){
		return userMapper.getPlazaCartCount(memberId);
	}


	@Cacheable(value="myalrimList", cacheManager = "ehCacheManager")
	public List<AlrimResponseDto> myalrimList(AlrimRequestDto myalrimRequestDto) {

		List<AlrimResponseDto> alrim = userMapper.myalrimList(myalrimRequestDto);

		for(AlrimResponseDto dto : alrim){
			LocalDateTime date = dto.getAindate();
			LocalDateTime now = LocalDateTime.now();
			Duration dutation = Duration.between(date,now);
			System.out.println("seconds : "+dutation.getSeconds());
			float resultDate = (float) dutation.getSeconds() / (60 * 60 * 24);
			String resultAgo = "";
			System.out.println("resultDate : "+resultDate);
			if(resultDate >= 365){
				resultAgo = (int)(resultDate/ 365)+"년 전";
			}else if(resultDate >= 30 && resultDate < 365 ){
				resultAgo = (int)(resultDate / 30)+"달 전";
			}else if(resultDate >= 1 && resultDate < 30){
				resultAgo = (int)(resultDate)+"일 전";
			}else if( resultDate < 1){
				resultDate = resultDate * (60 * 60 * 24); //초로 변환
				if(resultDate <60){ //초단위
					resultAgo = "지금";
				}else if (resultDate >= 60 && resultDate < 3600){ //분
					resultAgo = (int)(resultDate / 60)+"분 전";
				}else if (resultDate >= 3600){ // 시간
					resultAgo = (int)(resultDate / (60 *24))+"시간 전";
				}
			}
			dto.setResultAgo(resultAgo);
		}
		return alrim;
	}

	public CustomUserDetailsDto findPlazaSubMember(CustomUserDetailsDto customUserDetailsDto){
		return userMapper.findPlazaSubMember(customUserDetailsDto);
	}

	public CustomUserDetailsDto getUserInfo(String memberId) {
		return userMapper.getUserInfo(memberId);
	}

	public DeleveryaddressDto getDeliveryAddress(String memberId) {
		return userMapper.getDeliveryAddress(memberId);
	}

	public List<CustomUserDetailsDto> getUserInfoByMobile(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.getUserInfoByMobile(customUserDetailsDto);
	}

	public int insertUserTask(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.insertUserTask(customUserDetailsDto);
	}

	public CustomUserDetailsDto selectExistSnsUser(CustomUserDetailsDto user) {
		return userMapper.selectExistSnsUser(user);
	}

	public int insertUserSns(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.insertUserSns(customUserDetailsDto);
	}


	*/
/*public CustomUserDetailsDto getUserByOrderId(String orderId) {

		CustomUserDetailsDto user = userMapper.getUserByOrderId(orderId);

		user.setType("member");

		user.setCompId(user.getCompanyId());
		user.setCompName(user.getUcompany());
		user.setMemId(user.getUid());
		user.setUid(user.getUid());
		user.setMemName(user.getName());
		//user.setMemLevel(user.getMemberLevel());
		//user.setSubLevel(1);
		user.setEmail(user.getEmail());
		user.setComp(user.getUcompany());
		user.setMobile(user.getUmobile());
		user.setPhone(user.getPhoneNo());
		user.setUserLevel(3);
		user.setUserPk(user.getMpk());

		return user;
	}*//*


	public List<UserTaskDto> getUserTask(UserTaskDto userTaskDto) {
		return userMapper.getUserTask(userTaskDto);
	}

	public List<UserSnsDto> getUserSns(UserSnsDto userSnsDto) {
		return userMapper.getUserSns(userSnsDto);
	}

	public int snsDisconnect(UserSnsRequestDto userSnsRequestDto) {
		return userMapper.snsDisconnect(userSnsRequestDto);
	}

	public Integer checkExistSmartPinClubMember(CustomUserDetailsDto user) {
		return userMapper.checkExistSmartPinClubMember(user);
	}

    public List<UserCompanyDeptResponseDto> getUserCompanyDeptByUserCompanySeq(UserCompanyDeptRequestDto userCompanyDeptRequestDto) {
		return userMapper.getUserCompanyDeptByUserCompanySeq(userCompanyDeptRequestDto);
    }

	public CustomUserDetailsDto getUserByPrpk(Integer rupk) {
		return userMapper.getUserByPrpk(rupk);
	}

	public UserCompanyDeptResponseDto getUserCompanyDept(UserCompanyDeptRequestDto userCompanyDeptRequestDto) {
		return userMapper.getUserCompanyDept(userCompanyDeptRequestDto);
	}

	public List<UserCompanyMemberResponseDto> getUserCompanyMemberListByUserCompanySeq(UserCompanyMemberRequestDto userCompanyMemberRequestDto) {
		return userMapper.getUserCompanyMemberListByUserCompanySeq(userCompanyMemberRequestDto);
	}

	public int insertUserCompanyMember(UserCompanyMemberRequestDto userCompanyMemberRequestDto) {
		return userMapper.insertUserCompanyMember(userCompanyMemberRequestDto);
	}

	public int updateUserInfoByUid(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.updateUserInfoByUid(customUserDetailsDto);
	}

	public CustomUserDetailsDto getCompanyIdByUserCompanySeq(Integer userCompanySeq) {
		return userMapper.getCompanyIdByUserCompanySeq(userCompanySeq);
	}

	public UserCompanyResponseDto getUserCompanyByBizNo(UserCompanyRequestDto userCompanyDto) {
		return userMapper.getUserCompanyByBizNo(userCompanyDto);
	}

	public UserCompanyResponseDto getUserCompanyByUserCompanySeq(UserCompanyRequestDto userCompanyRequest) {
		return userMapper.getUserCompanyByUserCompanySeq(userCompanyRequest);
	}

	public ChatMasterRequestResponseDto getChatCount(ChatMasterRequestResponseDto chatMasterRequestResponseDto) {
		return userMapper.getChatCount(chatMasterRequestResponseDto);
	}

	public Integer insertPlazaSubMember(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.insertPlazaSubMember(customUserDetailsDto);
	}
	public List<UserCompanyManagerAuthFunctionResponseDto> getUserCompanyManagerAuthFunctionList(String memberId) {
		return userMapper.getUserCompanyManagerAuthFunctionList(memberId);
	}

	public UserCompanyManagerAuthResponseDto getUserCompanyManagerAuth(String memberId) {
		return userMapper.getUserCompanyManagerAuth(memberId);
	}

	public UserCompanyMemberResponseDto userCompanyMemberByUserCompanySeq(UserCompanyMemberRequestDto userCompanyRequestDto) {
		return userMapper.userCompanyMemberByUserCompanySeq(userCompanyRequestDto);
	}

	public int updateUserInfo(CustomUserDetailsDto customUserDetailsDto) {
		return userMapper.updateUserInfo(customUserDetailsDto);
	}

	public List<Integer> getUserCompanyMemberDeptSeqList(Integer deptSeq) {
		return userMapper.getUserCompanyMemberDeptSeqList(deptSeq);
	}

	public List<LoginDto> selectUserInfoLIst(UserInfoRequestDto userInfoRequestDto) {
		return userMapper.selectUserInfoLIst(userInfoRequestDto);
	}
}
*/
