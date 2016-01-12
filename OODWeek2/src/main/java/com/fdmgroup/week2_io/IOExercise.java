package com.fdmgroup.week2_io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOExercise {

	private static final String FILE_USERS_DATA = "users.txt";

	public int countCharacter(char c, String fileName) {
		Scanner sc = null;
		int count = 0;
		try {
			sc = new Scanner(new File(fileName));
			while(sc.hasNext()) {
				String line = sc.nextLine();
				for(int i = 0; i < line.length(); i++) {
					if(c == line.charAt(i)) {
						count++;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(sc != null) {
				sc.close();
			}
		}

		return count;
	}

	public boolean registerUser(String username, String email, String address) {
//		Scanner sc = null;
		try {
//			sc = new Scanner(System.in);
//
//			System.out.println("Create new user");
//			System.out.println("Please enter user name: ");
//			String username = sc.nextLine();
//			System.out.println("Please enter user email: ");
//			String email = sc.nextLine();
//			System.out.println("Please enter user address: ");
//			String address = sc.nextLine();

			File file = new File(FILE_USERS_DATA);

			String content = "";

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} else {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String s;
				while((s = br.readLine()) != null) { 
					if(content.length() == 0) {
						content = s;
					} else {
						content = content + "\n" + s;
					}
				} 
				br.close();
				content += "\n";
			}

			content = content + username + "," + email + "," + address;

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
//			if(sc != null) {
//				sc.close();
//			}
		}
		
		return true;
	}

	public List<User> getUsers() {
		Scanner sc = null;
		List<User> users = new ArrayList<User>();
		try {
			sc = new Scanner(new File(FILE_USERS_DATA));
			while(sc.hasNext()) {
				String line = sc.nextLine();
				String data[] = line.split(",");
				if(data != null & data.length > 2) {
					User user = new User();
					user.setUsername(data[0]);
					user.setEmail(data[1]);
					user.setAddress(data[2]);
					users.add(user);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(sc != null) {
				sc.close();
			}
		}

		return users;
	}
}
