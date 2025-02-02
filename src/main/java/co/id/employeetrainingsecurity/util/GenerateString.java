package co.id.employeetrainingsecurity.util;

import java.util.Random;

public class GenerateString {
	
	public static String generateOtp() {
		String otp = String.valueOf(new Random().nextInt(999999));
		return otp;
	}

}
