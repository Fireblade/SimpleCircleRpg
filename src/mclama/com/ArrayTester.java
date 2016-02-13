package mclama.com;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;




abstract class ArrayTester implements ArrayInterface {
	public static void main(String[] arg) {
		// data members
		int size;
		int objectO;

		// Scanner
		Scanner input = new Scanner(System.in);

		

		// get size of array
		System.out.print("Enter size of array:");
		size = input.nextInt();
		Array[] Arrayobjects = new Array[size];
		for (int i = 0; i < size; i++) {

			System.out.print("Enter integer objects: ");
			Arrayobjects[i] = new Array();
			Arrayobjects[i].setObjectO(input.nextInt());

		}
		System.out.print("Array List of Objects: " + getObjects(Arrayobjects));

	}
	
	private static String getObjects(Array[] Arrayobjects){
		String text = "";
		for (int i = 0; i < Arrayobjects.length; i++) {
			text += (Arrayobjects[i].getObjectO() + " ");
		}
		return text;
	}
}
