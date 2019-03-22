package com.lnod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadAndWriteExcel {

	static List<UserModel> userRecordList1 = new ArrayList<UserModel>();
	static List<UserModel> userRecordList2 = new ArrayList<UserModel>();
	static final String DELIMETER = ";";
	public static void main(String[] args) {
		ReadAndWriteExcel readAndWriteExcel = new ReadAndWriteExcel();
		readAndWriteExcel.readFile("D:\\Mailer\\classroom_data.xlsx", 1);
		readAndWriteExcel.readFile("D:\\Mailer\\online_courses.xlsx", 2);
		
		if(!userRecordList1.isEmpty() && !userRecordList2.isEmpty()) {
			readAndWriteExcel.mergeBothList(userRecordList1, userRecordList2);
		}
	}

	private void readFile(String fileName, int listNumber) {
		try
		{
			FileInputStream file = new FileInputStream(new File(fileName));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			boolean firstRow = true;
			while (rowIterator.hasNext()) {
				if(firstRow) {
					firstRow = false;
					rowIterator.next();
					continue;
				}
				
				Row row = rowIterator.next();
				saveRecords(row, listNumber);
			}
			file.close();
			System.out.println("Reading file completed successfully.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveRecords(Row row, int listNumber) {
		//For each row, iterate through all the columns
		Iterator<Cell> cellIterator = row.cellIterator();
		int cellNum = 1;
		String email = "";
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			if(cellNum == 1) {
				email = cell.getStringCellValue();
			}
			else if(cellNum == 2) {
				if(listNumber == 1) {
					String cellValue = cell.getStringCellValue();
					String[] courseNameList = cellValue.split(DELIMETER);
					for(String courseName : courseNameList) {
						UserModel userRecord = new UserModel(email.trim(), courseName.trim(), "");
						userRecordList1.add(userRecord);
					}
				} else {
					String onlineCourse = cell.getStringCellValue();
					UserModel userRecord = new UserModel(email.trim(), "", onlineCourse.trim());
					userRecordList2.add(userRecord);
				}
				break;
			}
			cellNum++;
		}
	}
	
	private void mergeBothList(List<UserModel> userRecordList1, List<UserModel> userRecordList2) {
		List<UserModel> userRecordList = new ArrayList<UserModel>();
		
		Collections.sort(userRecordList1, new EmailComparator());
		Collections.sort(userRecordList2, new EmailComparator());
		Collections.sort(userRecordList2, new EmailComparator());
		
		
		System.out.println("===============");
		System.out.println(userRecordList1.get(0).getEmailId());
		System.out.println(userRecordList1.get(11).getEmailId());
		System.out.println(userRecordList1.get(12).getEmailId());
		System.out.println(userRecordList1.get(13).getEmailId());
		System.out.println(userRecordList1.get(14).getEmailId());
		System.out.println(userRecordList1.get(15).getEmailId());
		System.out.println("-----------");
		System.out.println(userRecordList2.get(0).getEmailId());
		System.out.println(userRecordList2.get(1).getEmailId());
		System.out.println(userRecordList2.get(2).getEmailId());
		System.out.println(userRecordList2.get(3).getEmailId());
		System.out.println(userRecordList2.get(4).getEmailId());
		System.out.println(userRecordList2.get(5).getEmailId());
		
		int i = 0, j = 0;
		int len1 = userRecordList1.size();
		int len2 = userRecordList2.size();
		
		while(i < len1 && j < len2) {
			UserModel user1 = userRecordList1.get(i);
			UserModel user2 = userRecordList2.get(j);
			if(user1.getEmailId().compareTo(user2.getEmailId()) == 0) {
				UserModel userRecord = new UserModel(user1.getEmailId(), user1.getClassroomSessionAttended(), user2.getOnlineCoursesEnrolled());
				userRecordList.add(userRecord);
				i++;
				j++;
			} else if(user1.getEmailId().compareTo(user2.getEmailId()) > 0) {
				UserModel userRecord = new UserModel(user2.getEmailId(), "", user2.getOnlineCoursesEnrolled());
				userRecordList.add(userRecord);
				j++;
			} else {
				UserModel userRecord = new UserModel(user1.getEmailId(), user1.getClassroomSessionAttended(), "");
				userRecordList.add(userRecord);
				i++;
			}
			
		}
		
		while(i < len1) {
			UserModel user1 = userRecordList1.get(i);
			UserModel userRecord = new UserModel(user1.getEmailId(), user1.getClassroomSessionAttended(), "");
			userRecordList.add(userRecord);
			i++;
		}
		
		while(j < len2) {
			UserModel user2 = userRecordList2.get(j);
			UserModel userRecord = new UserModel(user2.getEmailId(), "", user2.getOnlineCoursesEnrolled());
			userRecordList.add(userRecord);
			j++;
		}
		
		//writeFile(userRecordList);
	}
	
	private void writeFile(List<UserModel> userRecordList) {
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Classroom And Enrolled Courses");

		//Header
		Row row = sheet.createRow(0);
		Cell headerCell = row.createCell(0);
		headerCell.setCellValue("Email ID");
		headerCell = row.createCell(1);
		headerCell.setCellValue("Classroom Sessions Attended");
		headerCell = row.createCell(2);
		headerCell.setCellValue("Online Courses Enrolled");
		
		int rownum = 1;
		for (UserModel userRecord : userRecordList) {
			row = sheet.createRow(rownum);

			Cell cellEmail = row.createCell(0);
			cellEmail.setCellValue(userRecord.getEmailId());
			Cell cellCourseName = row.createCell(1);
			cellCourseName.setCellValue(userRecord.getClassroomSessionAttended());
			Cell cellOnlineCourses = row.createCell(2);
			cellOnlineCourses.setCellValue(userRecord.getOnlineCoursesEnrolled());
			
			rownum++;
		}
		try {
			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File("D:\\Mailer\\Classroom_And_OnlineCourses_Data.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("File written successfully on disk.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
