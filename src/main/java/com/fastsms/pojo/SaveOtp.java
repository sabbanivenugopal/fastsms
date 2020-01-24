package com.fastsms.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="otp")
public class SaveOtp {
	
	@Id
	private String mobile2;
	private String otp1;
	
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getOtp1() {
		return otp1;
	}
	public void setOtp1(String otp1) {
		this.otp1 = otp1;
	}

}
