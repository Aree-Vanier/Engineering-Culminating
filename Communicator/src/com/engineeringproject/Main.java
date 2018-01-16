package com.engineeringproject;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import arduino.Arduino;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class Main {
	
	static Kinect myKinect;
	static JLabel label1 = new JLabel("x : " + "0");
	static JLabel label2 = new JLabel("y : " + "0");
	static JLabel label3 = new JLabel("z : " + "0");
	static JFrame f;
	
	public Arduino arduino;
	
	public static void main(String[] args) {   	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
	
	private Main() {
		
		arduino = new Arduino("COM6", 9600);
		arduino.openConnection();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		f = new JFrame();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setMinimumSize(new Dimension(300,150));
		f.add(label1);
		f.add(label2);
		f.add(label3);
		
		f.setVisible(true);
		
		label1.setFont(new Font("Serif", Font.PLAIN, 24));
		label2.setFont(new Font("Serif", Font.PLAIN, 24));
		label3.setFont(new Font("Serif", Font.PLAIN, 24));
		
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		label2.setAlignmentX(Component.CENTER_ALIGNMENT);
		label3.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		f.setTitle("Intitializing Kinect...");
		
		myKinect = new Kinect(this);
		
		if(!myKinect.start(J4KSDK.DEPTH | J4KSDK.SKELETON)){
			f.setTitle("Kinect not detected");
			JOptionPane.showMessageDialog(f, "There was no Kinect sensor detected in your system.");
		} else {
			f.setTitle("Kinect Communicator");
		}
	}

}