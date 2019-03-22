package com.lnod;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailComparator implements Comparator<UserModel>{

	
	public static final EmailComparator INSTANCE =
	        new EmailComparator();

	    private static final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)");

	    public int compare(UserModel o1, UserModel o2) {
	    	String s1 = o1.getEmailId().substring(0, o1.getEmailId().indexOf("@"));
	    	String s2 = o2.getEmailId().substring(0, o2.getEmailId().indexOf("@"));
	        Matcher m1 = PATTERN.matcher(s1);
	        Matcher m2 = PATTERN.matcher(s2);

	        // The only way find() could fail is at the end of a string
	        while (m1.find() && m2.find()) {
	            // matcher.group(1) fetches any non-digits captured by the
	            // first parentheses in PATTERN.
	            int nonDigitCompare = m1.group(1).compareTo(m2.group(1));
	            if (0 != nonDigitCompare) {
	                return nonDigitCompare;
	            }

	            // matcher.group(2) fetches any digits captured by the
	            // second parentheses in PATTERN.
	            if (m1.group(2).isEmpty()) {
	                return m2.group(2).isEmpty() ? 0 : -1;
	            } else if (m2.group(2).isEmpty()) {
	                return +1;
	            }

	            BigInteger n1 = new BigInteger(m1.group(2));
	            BigInteger n2 = new BigInteger(m2.group(2));
	            int numberCompare = n1.compareTo(n2);
	            if (0 != numberCompare) {
	                return numberCompare;
	            }
	        }

	        // Handle if one string is a prefix of the other.
	        // Nothing comes before something.
	        return m1.hitEnd() && m2.hitEnd() ? 0 :
	               m1.hitEnd()                ? -1 : +1;
	    }
	
	
	
	
//	public int compare(UserModel o1, UserModel o2) {
//		// TODO Auto-generated method stub
//		return o1.getEmailId().compareTo(o2.getEmailId());
//	}

	//	public int compare(UserModel o1, UserModel o2) {
	//		//return o1.getEmailId().compareTo(o2.getEmailId());
	//		String email1 = o1.getEmailId();
	//		String email2 = o2.getEmailId();
	////		if(!email1.matches(".*\\d+.*") && !email2.matches(".*\\d+.*"))
	////			return email1.compareTo(email2);
	//        return (extractInt(email1) - extractInt(email2));
	//	}
	//	
	//
	//    int extractInt(String email) {
	//        String num = email.replaceAll("\\D", "");
	//        System.out.println(num);
	//        // return 0 if no digits found
	//        return num.isEmpty() ? 0 : Integer.parseInt(num);
	//    }

//	public int compare(UserModel u1, UserModel u2) {
//		String o1 = u1.getEmailId();
//		String o2 = u2.getEmailId();
//		int i1 = this.getRearInt(o1);
//		int i2 = this.getRearInt(o2);
//		String s1 = getTrailingString(o1);
//		String s2 = getTrailingString(o2);
//
//		if(i1 == i2)
//			return s1.compareTo(s2);
//		if(i1>i2)
//			return 1;
//		else if(i1<i2)
//			return -1;
//		return 0;
//	}
//
//	private int getRearInt(String s) {
//		s=s.trim();
//		int i=Integer.MAX_VALUE;
//		try {
//			i = Integer.parseInt(s.replaceAll("[^0-9]", ""));
//		} catch(ArrayIndexOutOfBoundsException e) {
//
//		} catch(NumberFormatException f) {
//			return i;
//		}
//
//		return i;
//	}
//
//	private String getTrailingString(String s) {
//		return  s.replaceFirst("[0-9]", "");
//	}
}
