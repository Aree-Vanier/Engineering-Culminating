package com.engineeringproject;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class Kinect extends J4KSDK {
	
	public Skeleton skeletons[] = new Skeleton[6];
	
	int lastSignalX = -1;
	int lastSignalY = -1;
	
	public Main main;
	
	public Kinect(Main main) {
		this.main = main;
	}
	
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions,float[] orientations, byte[] joint_status) {
		if (skeletons == null) return;
		
		for(int i=0;i<this.getMaxNumberOfSkeletons();i++) {
			skeletons[i] = Skeleton.getSkeleton(i, skeleton_tracked, positions,orientations,joint_status, this);
			
			if (skeletons[i] != null && skeletons[i].isTracked()) {
				final float X = skeletons[i].get3DJointX(Skeleton.HEAD);
				final float Y = skeletons[i].get3DJointY(Skeleton.HEAD);
				final float Z = skeletons[i].get3DJointZ(Skeleton.HEAD);
				
				Thread thread = new Thread() {
					public void run() {
						if(X > 0) {
							if(lastSignalX != 0) {
								main.arduino.serialWrite("95|");
								lastSignalX = 0;
							}
						} else {
							if(lastSignalX != 1) {
								main.arduino.serialWrite("15|");
								lastSignalX = 1;
							}
						}
						
						if(Y > 0) {
							if(lastSignalY != 0) {
								main.arduino.serialWrite("59|");
								lastSignalY = 0;
							}
						} else {
							if(lastSignalY != 1) {
								main.arduino.serialWrite("51|");
								lastSignalY = 1;
							}
						}
					}
				};
				thread.start();
				
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