package com.station3.login.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 10L;

    //USER_INFO 항목들[s]
    protected int upk;
    protected Integer udept;
    protected String uname;

    //@NotNull(message = "아이디는 필수 값입니다.")
    protected String uid;

    //@NotNull(message = "비밀번호는 필수 값입니다.")
    protected String upasswd;
    protected String uemail;
    protected String unewsletter;
    protected String usnsType;
    protected String usnsId;
    protected Integer utype;
    protected String usex;
    protected String ubirthday;
    protected String umobile;
    protected String umobileCert;
    protected LocalDateTime umobileCertDate;
    protected String usms;
    protected String uphone;
    protected String uzone1;
    protected String uzone2;
    protected String uzipcode;
    protected String uaddress;
    protected String ucompany;
    protected String uregistrationNumber;
    protected String ufounder;
    protected String uduty;
    protected String udepartment;
    protected String uposition;
    protected String upicture;
    protected String ucompanyCeritify;
    protected LocalDateTime ucertifyDate;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime uregisterDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime uconfirmDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime uwithdrawDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime umodifyDate;

    protected Integer ustatus;
    protected String uprojNum;
    protected String uprojContractNum;
    protected String uporjIngNum;
    protected String uporjDoneNum;
    protected String uprojTotalPrice;
    protected String ufrom;
    protected String ufromId;
    protected String ufromJoinDate;
    protected String ubid;
    protected String agentId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime ulogindate;
    protected String incode;
    protected String mocode;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime modate;
    protected String ussoId;
    protected String rugPk;
    protected String uadminUseYn;

    //Plaza_member에서 USER_INFO로 옮긴 칼럼들
    protected Integer memberLevel;
    protected Integer userLevel;
    protected String userRole;
    protected String approval;
    protected int reserve;
    protected String license;
    protected String bizStatus;
    protected String bizSubject;
    protected String companyPhone;
    protected String companyFax;
    protected int credit;
    protected int bounds;
    protected String notify;
    protected int notifyBounds;
    protected String bank;
    protected String bankbook;
    protected String account;
    protected String depositor;
    protected String calcGbn;
    protected String calculate;
    protected String contract;
    protected String apprAuth;
    protected String secession;
    //protected LocalDateTime secessionDate; -> uWithdrawDate로 대체
    protected String secessionReason;
    protected String secessionMemo;
    protected String approvalAdmin;
    protected String joinAgreeTerms;
    protected String recommandId;
    //USER_INFO 항목들[e]

    //protected String memberId;     // '아이디',
    //protected String memberPw;     // '비밀번호',
    protected String ccode;     //
    protected String name;     // '이름',
    protected String email;     // '이메일',
    protected String zipCode;     // '우편번호',
    protected String address;     //
    protected String phoneNo;     // '전화번호',
    protected String autoMail;  // '메일수신여부',
    protected String code;            // '업체코드',
    protected String department;      //
    protected String depPosition;    //
    protected Integer level;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    protected LocalDateTime regDate; // '등록일',

    protected int msPk;
    protected String mapprovalAdmin; // '승인관리자',
    protected int mpk;

    protected String join_agree_privacy;
    protected String marketing_agree;
    protected String join_agree_marketing;
    protected LocalDateTime agree_indate;

    protected String userId;

    protected String sessionId;

    protected String companyId;
    protected Integer userCompanySeq;

    protected String spFlag;
    protected String spPaymethod;

    public void setUid(String uid) {
        uid = Optional.ofNullable(uid).orElse("");
        this.uid = uid.toLowerCase(); //소문자로 저장
    }

    public void setUemail(String uemail) {
        uemail = Optional.ofNullable(uemail).orElse("");
        this.uemail = uemail.toLowerCase(); //소문자로 저장
    }

    public LoginDto update(String name, String picture){
        this.uname = name;
        this.upicture = picture;

        return this;
    }
}
