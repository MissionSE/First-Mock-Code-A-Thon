package com.missionse.securityhelper.augmented;

import gl.Color;
import gl.GLCamera;
import gl.GLFactory;
import gl.MarkerObject;
import gl.scenegraph.MeshComponent;
import gl.scenegraph.Shape;

import java.util.LinkedList;
import java.util.List;

import util.Vec;
import worldData.Obj;

import com.missionse.augmented.setups.BasicMultiSetup;
import components.ProximitySensor;

public class GenerateMeshComponents {
	
	public static List<MeshComponent> getNextLocation(final BasicMultiSetup s){
		List<MeshComponent> retVal = new LinkedList<MeshComponent>();
		
		
		MeshComponent arrow = GLFactory.getInstance().newArrow();
		arrow.setPosition(new Vec(0, 0, 4));

		MeshComponent circle = GLFactory.getInstance().newCircle(
				Color.redTransparent());
		circle.setScale(new Vec(4, 4, 4));
		// circle.myAnimation = new AnimationPulse(2, new Vec(0, 0, 0), new
		// Vec(4, 4, 4), 0.2f);

		final MeshComponent itemMesh = new Shape();
		itemMesh.addChild(arrow);
		itemMesh.addChild(circle);
		itemMesh.setPosition(Vec.getNewRandomPosInXYPlane(s.getCamera().getPosition(),
				10, 15));

		Obj itemToCollect = new Obj();
		itemToCollect.setComp(new ProximitySensor(s.getCamera(), 3f) {
			@Override
			public void onObjectIsCloseToCamera(GLCamera myCamera2, Obj obj,
					MeshComponent m, float currentDistance) {
				itemMesh.setPosition(Vec.getNewRandomPosInXYPlane(
						s.getCamera().getPosition(), 5, 20));
			}
		});
		
		retVal.add(itemMesh);
		
		return retVal;
	}
	
	public static List<MarkerObject> getMarkers(BasicMultiSetup s){
		List<MarkerObject> retVal = new LinkedList<MarkerObject>();
		
		
		
		
		
		
		
		return retVal;
	}
	
	

}
