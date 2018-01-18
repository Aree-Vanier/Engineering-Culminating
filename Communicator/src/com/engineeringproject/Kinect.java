package com.engineeringproject;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class Kinect extends J4KSDK {
	
	public Skeleton skeletons[] = new Skeleton[6];
	public float X, Y, Z;
	
	int lastSignal = -1;
	
	public Main main;
	
	public Kinect(Main main) {
		this.main = main;
	}
	
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions,float[] orientations, byte[] joint_status) {
		if (skeletons == null) return;
		
		for(int i=0;i<this.getMaxNumberOfSkeletons();i++) {
			skeletons[i] = Skeleton.getSkeleton(i, skeleton_tracked, positions,orientations,joint_status, this);
			
			if (skeletons[i] != null && skeletons[i].isTracked()) {
				X = skeletons[i].get3DJointX(Skeleton.SPINE_MID);
				Y = skeletons[i].get3DJointY(Skeleton.SPINE_MID);
				Z = skeletons[i].get3DJointZ(Skeleton.SPINE_MID);
				
				if(X > 0) {
					if(lastSignal != 0) {
						main.arduino.serialWrite("95|");
						lastSignal = 0;
					}
				} else {
					if(lastSignal != 1) {
						main.arduino.serialWrite("15|");
						lastSignal = 1;
					}
				}
				
				if(Y > 0) {
					if(lastSignal != 2) {
						main.arduino.serialWrite("59|");
						lastSignal = 2;
					}
				} else {
					if(lastSignal != 3) {
						main.arduino.serialWrite("51|");
						lastSignal = 3;
					}
				}
				
				main.xLabel.setText("X : " + String.format("%.3f", X));
				main.yLabel.setText("Y : " + String.format("%.3f", Y));
				main.zLabel.setText("Z : " + String.format("%.3f", Z));
				
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