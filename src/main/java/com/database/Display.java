package com.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Display extends JFrame{

	private static final long serialVersionUID = 1L;
	private static JTextArea txt;
	JButton button = new JButton("Add");
	JButton print = new JButton("Print");
	ArrayList<Fee> listoffees = new ArrayList<>();
	private DatabaseReference exitDatabase;
	int ctr;
	int ctr2;

//	int ctr = 1;
	Display(){
		exitDatabase = FirebaseDatabase.getInstance(FirebaseDB.getExitRFID()).getReference();

		setLayout(new FlowLayout());
		txt = new JTextArea("test");
		ctr = 0;
		ctr2 = 0;
		add(button);
		add(print);
		add(txt);
		String[] ss = {"51:5C:AA:89","9D:CA:30:5B","15:CD:30:5B"};
		String[] zz = {"didn't load","loaded"};
		DatabaseReference ref = exitDatabase.child("Exit");


		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Exit exit = new Exit(ss[ctr],zz[ctr2], LocalDate.now().toString());
				ref.push().setValue(exit);

				ctr++;
				ctr2++;
				if(ctr == 3) ctr = 0;
				if(ctr2 == 2) ctr2 = 0;
			}
		});
//51:5C:AA:89
//9D:CA:30:5B
		//15:CD:30:5B
		print.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Fee f = new Fee("ABC123","alvinyu","50","150","12:12","69",
						"lul","9/11/17",true,true,true);
//				printOutSuccessful(f);
//				printaa();
			}
		});
		setSize(300,400);
		
		setVisible(true);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
}


//	private boolean printOutSuccessful(Fee f) {
//		try {
//			/**
//			 * Setting up strings for print arrangement
//			 */
//			String province = "CEBU";
//			String date = f.getDatePaid();
//			String payor = f.getBus_plate();
//			String nature1 = "Arrival Fee";
//			String nature2 = "Loading Fee";
//			String arrivalFee = f.getArrivalFee();
//			String loadingFee = f.getLoadingFee();
//			String total = "";
//			String cashier = f.getEmployeeID();
//			int tot = 0;
//
//			if(f.getPaidArrival()){
//				tot += 50;
//			}
//			if(f.getPaidLoading()){
//				tot += 150;
//			}
//
//			switch (tot){
//				case 50:  total = "FIFTY PESOS ONLY";
//					break;
//				case 150:  total = "ONE HUNDRED FIFTY PESOS ONLY";
//					break;
//				case 200:  total = "TWO HUNDRED PESOS ONLY";
//					break;
//				default: total = "";
//					break;
//			}
//
//			/**
//			 * Initializing file writer to write text file
//			 */
//			PrintWriter out = new PrintWriter("filename.txt");
//			out.println("\t" + province +
//					"\n\n\n" + date +
//					"\n" + payor +
//					"\n\n\n" + nature1 + "\t\t" + arrivalFee +
//					"\n" + nature2 + "\t\t" + loadingFee +
//					"\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t\t\t\t\t" + tot +
//					"\n" + total +
//					"\n" + "X" +
//					"\n\n\n\n\n\t        " + cashier);
//			out.close();
//
//			/**
//			 * Initializing file reader to read text file
//			 */
//			InputStream is = new FileInputStream("filename.txt");
//			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
//			String line = buf.readLine();
//			StringBuilder sb = new StringBuilder();
//
//			while (line != null) {
//				sb.append(line).append("\n");
//				line = buf.readLine();
//			}
//
//			/**
//			 * Displays contents on console
//			 */
//			String fileAsString = sb.toString();
//			System.out.println("Contents : " + fileAsString + " -END");
//
//			/**
//			 * Transfers txt file to EditorPane and prints out
//			 */
//			JEditorPane text = new JEditorPane("file:filename.txt");
//			PrintService service = PrintServiceLookup.lookupDefaultPrintService();
//			int two = 2;
////			Font myFont = new Font("Calibri", Font.PLAIN, 11);
////			text.setFont(myFont);
//
//			text.setMargin(new Insets(1, (1/3), (1/2), (1/4)));
//			text.print(null, null, false, service, null, false);
////			noPlate.setText("");
//			return true;
//
//		} catch (FileNotFoundException e) {
//			System.out.println("No file");
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			System.out.printf("IOException");
//			e.printStackTrace();
//			return false;
//		} catch (PrinterAbortException e) {
//			System.out.println("Printer Aborted");
////			noPlate.setText("* - Printing Aborted!");
//			System.out.println("Printer Aborted");
//			e.printStackTrace();
//			return false;
//		} catch (PrinterException e) {
//			System.out.println("PrinterException");
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	public static void main(String[] args) throws IOException {
		FirebaseDB.initFirebase();
		new Display();
	 
	    }

}
