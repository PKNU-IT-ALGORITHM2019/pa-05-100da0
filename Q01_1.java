package assignment6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Q01_1 {

	static TreeNode wordsroot;    // 단어+설명  저장

	static int N=0;


	public static void main(String [] args) {	

		makeNode( "shuffled_dict.txt" );


		Scanner kb = new Scanner(System.in);

		while( true ) { 
			System.out.print("$ ");
			String command = kb.next();

			if(command.equals("add")) {
				String arr[]= new String[3];
				kb.nextLine();		
				System.out.print("word: ");
				arr[0]= kb.nextLine();
				System.out.print("class: ");
				String flag = kb.nextLine();
				if(flag.equals("")) 
					arr[1]="()";
				else arr[1]= flag;
				System.out.print("meaning: ");
				arr[2]= kb.nextLine();
				addWord(arr);

			}


			else if(command.equals("size")) {		
				System.out.println(N);	
			}

			else if(command.equals("delete")) {
				String find = kb.nextLine().substring(1)+" ";
				if(delete(find)) {
					System.out.println("Deleted successfully.");
					N--;}
				else System.out.println("Not found.");

			}
			
			else if(command.equals("deleteall")) {
				String fileName = kb.next();
				deleteAll( fileName );
			}
			else if(command.equals("print")) {
				printdata(wordsroot);
			}

			else if(command.equals("exit")) 
				break;

			else System.out.println("잘못된 입력");

		}
		kb.close();

	}

	static void makeNode( String fileName ){		
		try {
			File file = new File( fileName);
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = bufReader.readLine();

			wordsroot=makeBTw(line);
			N++;

			while((line = bufReader.readLine()) != null){
				TreeNode W = makeBTw(line);
				treeInsert(W);
				N++;
			}
			bufReader.close();	 
			//System.out.println("Success");

		}catch(FileNotFoundException e){
			System.out.println("No file");
			return; 
		}catch(IOException e){
			System.out.println(e);
		}
	}

	public static void treeInsert(TreeNode w) {
		TreeNode y = null;
		TreeNode x = wordsroot;
		String[] arr= w.data.split("\\(");

		while(x!=null) {
			String[] arr2= x.data.split("\\(");
			y=x;
			if( arr[0].compareToIgnoreCase(arr2[0]) < 0)
				x=x.left;
			else x=x.right;
		}
		w.parent=y;
		String[] arr3= y.data.split("\\(");
		if( arr[0].compareToIgnoreCase(arr3[0]) < 0) 
			y.left=w;
		else y.right=w;

	}

	public static TreeNode makeBTw(String data){
		TreeNode makew = new TreeNode(data);
		return makew;
	}
	static void addWord( String[]arr ) {
		String tmp = arr[0]+" "+arr[1]+" "+arr[2];
		TreeNode W = makeBTw(tmp);
		treeInsert(W);
		N++;
	}

	public static boolean delete(String key) {
		TreeNode p = wordsroot;
		TreeNode deleteNode = null;
		TreeNode parent = null;  

		while(p != null) {
			String[] arr= p.data.split("\\(");
			if(arr[0].compareToIgnoreCase(key)==0) { 
				deleteNode = p; 
				//System.out.println(arr[0]);
				break;
			}
			parent = p;
			if(arr[0].compareToIgnoreCase(key) < 0) { 
				p = p.right; 
			}else { 
				p = p.left; 
			}
		}

		if(deleteNode == null) {return false;} 

		if(deleteNode.left == null && deleteNode.right == null) {
			if(parent.left == deleteNode) {
				parent.left=null;
			}else {
				parent.right=null;
			}
		}else if(deleteNode.left == null || deleteNode.right == null) {
			if(deleteNode.left != null) { 
				if(parent.left ==deleteNode ) { 
					parent.left=deleteNode.left; 
				}else {
					parent.right=deleteNode.left;
				}
			}else { 
				if(parent.left ==deleteNode ) { 
					parent.left=deleteNode.right; 
				}else { 
					parent.right=deleteNode.right;
				}
			}
		}
		else {
			TreeNode q = deleteNode.left; 

			deleteNode.data=q.data;
			delete(deleteNode.data); 
		}
		return true;
	}

	static void deleteAll (String fileName) {
		try {
			File file = new File( fileName);
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = bufReader.readLine();

			while((line = bufReader.readLine()) != null){
				if(delete(line+" "))
				N--;			
			}
			bufReader.close();	 

		}catch(FileNotFoundException e){
			System.out.println("No file");
			return; 
		}catch(IOException e){
			System.out.println(e);
		}
		
	}
	static void printdata(TreeNode root) {	
		
		if(root!=null){	
			printdata(root.left);
			System.out.println(root.data);
			printdata(root.right);		
		}
	
	}



}
