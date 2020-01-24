package com.fastsms.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public class GenerateOtp {
	
	public static void main(String[] args) 
    { 
        int length = 4; 
        System.out.println(geek_Password(length)); 
    } 
	@RequestMapping(value="/sendSmsJson",method=RequestMethod.POST)
    static char[] geek_Password(int len) 
    { 
        System.out.println("Generating password using random() : "); 
        System.out.print("Your new password is : "); 
    
        String numbers = "0123456789ABCDEFGHIJKLM"; 
             
        Random rndm_method = new Random(); 
  
        char[] otp = new char[len]; 
  
        for (int i = 0; i < len; i++) 
        { 
           
            otp[i] = 
              numbers.charAt(rndm_method.nextInt(numbers.length())); 
        } 
        return otp; 
    } 

}
