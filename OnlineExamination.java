package com.main;
import java.util.Scanner;

public class OnlineExamination {
	
	private String userName;
	private String password;
	private boolean isLoggedIn;
	private int timeRemaining;
	private int questionCount;
	private int[] userAnswer;
	private int[] correctAnswer;
	
	public OnlineExamination(String userName , String password) {
		this.userName = userName;
		this.password = password;
		System.out.println("You are registered successfully....");
		this.isLoggedIn = false;
		this.timeRemaining = 30;  //time in minutes
		this.questionCount = 10;
		this.userAnswer = new int[questionCount];
		this.correctAnswer = new int[questionCount];
		
		//initialize correct answer with random values 0 / 1
		for(int i = 0; i < questionCount;i++) {
			correctAnswer[i] =(int) Math.round(Math.random());
		}
	}
	public void login() {
		System.out.println("You have to login to give exam ....");
		Scanner sc = new Scanner(System.in);
		System.out.println("User Name : ");
		String inputUserName = sc.nextLine();
		System.out.println("Password : ");
		String inputPassword = sc.nextLine();
		if(inputUserName.equals(userName) && inputPassword.equals(password)) {
			isLoggedIn = true;
			System.out.println("Login successful!! . All the best....  ");
		}
		else {
			System.out.println("Login failed. Please try again.. " + "Carefully!!" );
		}
	}
	
	public void logout() {
		isLoggedIn = false;
		System.out.println("Logout successfully..");
	}
	
	public void startExam() {
		if(!isLoggedIn) {
			System.out.println("Please login first");
		
		}
		Scanner scn = new Scanner(System.in);
		System.out.println(" You have" + timeRemaining + "minutes to complete the exam.");
		for(int i = 0;i < questionCount; i++) {
			System.out.println("Question" + (i+1) + ";");
			System.out.println("1. Option 1");
			System.out.println("2. Option 2");
			System.out.println("3. Option 3");
			System.out.println("4. Option 4");
			System.out.println("Your answer (1 or 2 or 3 or 4): ");
			int answer = scn.nextInt();
			userAnswer[i] = answer;
		}
		System.out.println("Would you like to submit ?  \n1:Yes  \n2:No ");
		int n = scn.nextInt();
		if(n==1) {
			submitExam();
		}
		else {
			//auto submit exam when time is up
			try {
				Thread.sleep(timeRemaining* 10 * 1000);
			}
			catch(InterruptedException e){
				e.printStackTrace();
				submitExam();
			}
		}
	}
	public void submitExam() {
		if(!isLoggedIn) {
			System.out.println("Please login first..");
			return;
		}
		int score = 0;
		for(int i =0;i < questionCount; i++) {
			if(userAnswer[i] == correctAnswer[i] ) {
				score++;
			}
		}
		System.out.println("Your score is " + score + " out of " + questionCount );
		System.out.println("All the best :");
		logout();
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your userName and password");
		String uName = scanner.nextLine();
		String pwd = scanner.nextLine();
		
		OnlineExamination exam = new OnlineExamination(uName , pwd);
		exam.login();
		exam.startExam();
	}

}
