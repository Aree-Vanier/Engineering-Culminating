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

public class Main {
	
	Kinect myKinect;
	JLabel xLabel = new JLabel("x : " + "0");
	JLabel yLabel = new JLabel("y : " + "0");
	JLabel zLabel = new JLabel("z : " + "0");
	JFrame f;
	
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
		f.add(xLabel);
		f.add(yLabel);
		f.add(zLabel);
		
		f.setVisible(true);
		
		xLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		yLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		zLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		
		xLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		yLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		zLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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