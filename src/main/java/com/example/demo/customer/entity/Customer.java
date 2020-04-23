package com.example.demo.customer.entity;

import com.example.demo.customer.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_ID")
    private Long createdId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_TIME")
    private LocalDateTime createdTime;

    @Column(name = "MODIFIED_ID")
    private Long modifiedId;

    @Column(name = "MODIFIED_BY")
    private String modifyBy;

    @Column(name = "MODIFIED_TIME")
    private LocalDateTime modifyTime;

    @Column(name = "SYSTEM_STATUS")
    @Enumerated(EnumType.STRING)
    private SystemStatus systemStatus;

    @Column(name = "ExpireDate")
    private LocalDateTime expireDate; // 車險欄位 逾期時間

    @Column(name = "AGREE_TIME")
    private LocalDateTime agreeTime;

    @Column(name = "CHANGE_PASSWORD_STATUS")
    private String changePasswordStatus;

    // 首次登入的判斷
    @Column(name = "IS_FIRST_TIME_LOGIN")
    private Boolean isFirstTimeLogin = Boolean.FALSE;

    @Column(name = "ROOT_CHANNEL_ID")
    private Long rootChannelId;

    @Column(name = "CHANNEL_ID")
    private Long channelId;

    @Column(name = "Name")
    private String localName;

    @Column(name = "ENG_NAME")
    private String engName;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "RegisterDate")
    private LocalDateTime registerDate;

    @Column(name = "LastActionDate")
    private LocalDateTime lastActionDate;

    @Column(name = "CREDIT_CARD_NO")
    private String creditCardNo;

    @Column(name = "CREDIT_CARD_EFF_TIME")
    private String creditCardEffTime;

    @Column(name = "LAST_CHANGE_PASSWORD_TIME")
    private LocalDateTime lastChangePasswordTime;

    @Column(name = "LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;

    @Column(name = "LAST_LOGIN_IP")
    private String lastLoginIp;

    @Column(name = "LOGIN_ERROR_COUNT")
    private Integer loginErrorCount = 0;

    @Column(name = "PID")
    private String idno;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "TELEXT")
    private String telext;

    @Column(name = "MobilePhone")
    private String mobile;

    @Column(name = "Email")
    private String email;

    @Column(name = "SEX")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "marriage")
    private Marriage marriage;

    @Column(name = "COUNTY_CODE")
    private String countyCode;

    @Column(name = "COUNTY_NAME")
    private String countyName;

    @Column(name = "Zip")
    private String zipCode;

    @Column(name = "ZIP_NAME")
    private String zipName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Birthday")
    private LocalDate birthday;

    @Column(name = "BROKER_CODE")
    private String brokerCode;

    @Column(name = "AGENT_CODE")
    private String agentCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status; // Y, N

    // OTP驗證是否成功
    @Enumerated(EnumType.STRING)
    @Column(name = "OTP_STATUS")
    private OtpStatus otpStatus = OtpStatus.Y;

    /**
     * (自己的)推薦人代碼
     */
    @Column(name = "MY_REFER_CODE")
    private String myReferCode;

    /**
     * OTP 通過日期時間
     */
    @Column(name = "OTP_PASS_TIME")
    private LocalDateTime otpPassTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE_CODE")
    private MemberSourceCode sourceCode = MemberSourceCode.INTERNET; // INTERNET,
    // ORIGIN,
    // COUNTER

    // 2017.07.26新增車籍資料相關欄位
    // 廠牌名稱
    @Column(name = "BRAND_NAME")
    private String brandName;
    // 牌照號碼
    @Column(name = "LICENSE_PLATE_NUMBER")
    private String licensePlateNumber;
    // 車險到期日
    @Column(name = "CAR_INSUR_EXP_DATE")
    private LocalDate carInsurExpDate;

    // 多筆車籍資料
    @Lob
    @Column(name = "CAR_DETAIL")
    private String carDetail;

    // 會員序號，於首次登入、新註冊時給值
    @Column(name = "CUSTOMER_SEQ")
    private String customerSeq;

    // 參加活動
    @Lob
    @Column(name = "JOIN_ACTIVITY")
    private String joinActivity;

    // 推薦人代碼
    @Column(name = "RECMD_CODE")
    private String recmdCode;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "FILE_UPLOAD_TIME")
    private LocalDateTime fileUploadTime;

    @Column(name = "REMARK")
    private String remark;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return status.toString();
            }
        };
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return account;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }
}
