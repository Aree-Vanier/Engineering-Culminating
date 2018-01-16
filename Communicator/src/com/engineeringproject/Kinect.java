package com.engineeringproject;

import arduino.Arduino;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class Kinect extends J4KSDK {
	
	public Skeleton skeletons[] = new Skeleton[6];
	public float X, Y, Z;
	
	public Main main;
	
	public Kinect(Main main) {
		this.main = main;
	}
	
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions,float[] orientations, byte[] joint_status) {
		if (skeletons == null) return;
		
		for(int i=0;i<this.getMaxNumberOfSkeletons();i++) {
			skeletons[i] = Skeleton.getSkeleton(i, skeleton_tracked, positions,orientations,joint_status, this);
			
			if (skeletons[i] != null && skeletons[i].isTracked()) {
				X = skeletons[i].get3DJointX(Skeleton.HEAD);
				Y = skeletons[i].get3DJointY(Skeleton.HEAD);
				Z = skeletons[i].get3DJointZ(Skeleton.HEAD);
				
				if(X > 0) {
					main.arduino.serialWrite("95|");
				} else {
					main.arduino.serialWrite("15|");
				}
				
				if(Y > 0) {
					main.arduino.serialWrite("59|");
				} else {
					main.arduino.serialWrite("51|");
				}
				
				main.label1.setText("X : " + String.format("%.3f", X));
				main.label2.setText("Y : " + String.format("%.3f", Y));
				main.label3.setText("Z : " + String.format("%.3f", Z));
				
			}
		}
	}
	
	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		
	}
	
	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {
		
	}


}