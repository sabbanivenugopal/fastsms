package com.fastsms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.client.RestTemplate;

import com.fastsms.pojo.Account;
import com.fastsms.pojo.JsonMsgs;
import com.fastsms.pojo.Message;
import com.fastsms.pojo.SaveOtp;
import com.fastsms.pojo.TestSms;
import com.google.gson.Gson;

import mobilevalidation.Validation;

@Controller
public class SendSms {

	@RequestMapping(value = "/sendSms")
	public String sendFastSms(@RequestParam("mobile") String mobile, @RequestParam("text") String text, Model model) {

		System.out.println("entered mobile number is =" + mobile);
		System.out.println("enterd  discription=" + text);

		StringBuilder url = new StringBuilder("https://www.smsgatewayhub.com/api/mt/SendSMS?");
		url.append("APIKey=").append("lWkH7i8o50Odr0gE9VYU5w").append("&");
		url.append("senderid=").append("SMSTST").append("&");
		url.append("channel=").append("1").append("&");
		url.append("DCS=").append("0").append("&");
		url.append("flashsms=").append("0").append("&");
		url.append("number=").append(mobile).append("&");
		url.append("text=").append(text).append("&");
		url.append("route=").append("1").append("&");
		System.out.println(url);

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.GET, entity, String.class);

		String responseBody = result.getBody();

		System.out.println("Response from smsgate hub:" + result.getBody());

		Gson gson = new Gson();
		TestSms txtMsg = gson.fromJson(responseBody, TestSms.class);

		model.addAttribute("message", txtMsg.getErrorMessage());
		return "home";
	}

	@RequestMapping(value = "/sendSmsJson", method = RequestMethod.POST)
	public String sendSmsByJson(@RequestParam("mobile") String mobile, @RequestParam("text") String text, Model model) {
		System.out.println("entered mobile number is =" + mobile);
		System.out.println("enterd  discription=" + text);

		StringBuilder url = new StringBuilder("https://www.smsgatewayhub.com/api/mt/SendSMS?");

		JsonMsgs sendSms = prepareSendSms(mobile, text);

		Gson gson = new Gson();
		String json = gson.toJson(sendSms);

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);

		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.POST, entity, String.class);

		String responseBody = result.getBody();
		TestSms txtMsg = gson.fromJson(responseBody, TestSms.class);

		model.addAttribute("message", txtMsg.getErrorMessage());

		return "home";
	}

	public JsonMsgs prepareSendSms(String mobile, String text) {
		JsonMsgs jm = new JsonMsgs();

		Account ac = new Account();

		ac.setUser("venugopal");
		ac.setPassword("606173");
		ac.setSenderId("SMSTST");
		ac.setChannel("1");
		ac.setDCS("0");

		List<Message> Msg = new ArrayList<Message>();
		Message mg = new Message();

		mg.setNumber(mobile);
		mg.setText(text);
		Msg.add(mg);

		jm.setAccount(ac);
		jm.setMessages(Msg);

		return jm;

	}

	@RequestMapping(value = "/generateOtp", method = RequestMethod.POST)
	public String generatingOtp(@RequestParam("mobile") String mobile, Model model) {

		System.out.println("Generating password using random() : ");

		Validation valid = new Validation();
		boolean validMobile = valid.isValidMobile(mobile);

		if (!validMobile) {
			model.addAttribute("msg", "Invalid mobile number please enter valid mobile number!!");
			System.out.println("mobile num is not valid!!");
			return "generateOtp";
		}

		String numbers = "0123456789";

		Random rndm_method = new Random();

		char[] otp = new char[mobile.length()];

		for (int i = 0; i < mobile.length() - 4; i++) {

			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}

		String otp1 = String.valueOf(otp);

		String mobile2 = String.valueOf(mobile);

		sendSmsByJson(mobile2, otp1, model);

		Session session = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		SaveOtp sv = new SaveOtp();
		sv.setMobile2(mobile2);
		sv.setOtp1(otp1);
		session.save(sv);
		tx.commit();
		session.close();

		return "verifyOtp";
	}

	@RequestMapping(value = "/verifyOtp", method = RequestMethod.POST)
	public String verifyDetails(@RequestParam("otp") String otp) {

		Session session = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from SaveOtp where otp1=?");
		query.setParameter(0, otp);
		List list = query.list();
		if (!list.isEmpty()) {
			System.out.println("otp matched..!!");
			return "profile";
		}
		System.out.println("otp mismatched..!!");

		return "generateOtp";
	}
}
