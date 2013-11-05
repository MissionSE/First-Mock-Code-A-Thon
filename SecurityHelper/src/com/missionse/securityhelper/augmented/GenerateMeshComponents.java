package com.missionse.securityhelper.augmented;

import gl.Color;
import gl.GLCamera;
import gl.GLFactory;
import gl.MarkerObject;
import gl.animations.AnimationFaceToCamera;
import gl.scenegraph.MeshComponent;
import gl.scenegraph.Shape;
import gui.simpleUI.EditItem;
import gui.simpleUI.ModifierGroup;
import gui.simpleUI.SimpleUIv1;

import java.util.LinkedList;
import java.util.List;

import util.IO;
import util.Vec;
import worldData.Obj;
import android.view.Gravity;

import com.missionse.augmented.components.MarkerObjectFactory;
import com.missionse.augmented.setups.BasicMultiSetup;
import com.missionse.augmentedreality.R;
import commands.Command;
import commands.ui.CommandShowToast;
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
	
	@Deprecated
	public static List<MarkerObject> getMarkers(BasicMultiSetup s){
		List<MarkerObject> retVal = new LinkedList<MarkerObject>();
		
		
		return retVal;
	}
	
	
	
	public static List<MeshComponent> getRandomDots(BasicMultiSetup s){
		List<MeshComponent> retVal = new LinkedList<MeshComponent>();
		
		for(int x = 0; x < 5; x++){
		MeshComponent goodCircle0 = GLFactory.getInstance().newCircle(Color.green());
		MeshComponent badCircle0 = GLFactory.getInstance().newCircle(Color.red());
		
		goodCircle0.setOnClickCommand(new CommandShowToast(s.getActivity(),"Everything is Good!"));
		badCircle0.setOnClickCommand(new CommandShowToast(s.getActivity(),"Malfuction"));
		
		goodCircle0.setPosition(Vec.getNewRandomPosInXYPlane(s.getCamera().getPosition(),
				20, 40));
		badCircle0.setPosition(Vec.getNewRandomPosInXYPlane(s.getCamera().getPosition(),
				15, 50));
		
		retVal.add(goodCircle0);
		retVal.add(badCircle0);
		}
		
		
		
		return retVal;
	}
	
	
	public static List<MarkerObject> getServerInfo(final BasicMultiSetup s){
		List<MarkerObject> retVal = new LinkedList<MarkerObject>();
		
		MeshComponent mesh = GLFactory.getInstance().newTexturedSquare("checkIdGood", IO.loadBitmapFromId(s.getActivity(), R.drawable.correctcirclegreen));
		mesh.setOnClickCommand(new CommandShowToast(s.getActivity(),"Everything is good"));
		mesh.addChild(new AnimationFaceToCamera(s.getCamera(), 0.5f));
		mesh.setScale(new Vec(2, 2, 2));
		retVal.add(MarkerObjectFactory.createTimeoutMarker(s, 4, mesh));
		
		MeshComponent mesh1 = GLFactory.getInstance().newTexturedSquare("checkIdBad", IO.loadBitmapFromId(s.getActivity(), R.drawable.warningcirclered));
		mesh1.setOnClickCommand(new Command() {

			@Override
			public boolean execute() {
				SimpleUIv1.showInfoScreen(s.getActivity(), new EditItem() {
					@Override
					public void customizeScreen(ModifierGroup group,
							Object message) {
						group.addModifier(new gui.simpleUI.modifiers.Headline(
								com.missionse.securityhelper.R.drawable.mselogo, "MSE Fix Guide"));
						group.addModifier(new gui.simpleUI.modifiers.InfoText(
								"This will be a place" + " to show the user how"
										+ " to fix " + " this item.", Gravity.CENTER));
						group.addModifier(new gui.simpleUI.modifiers.InfoText(
								"SecurityHelp @1990 MSE", Gravity.CENTER));
					}
				}, null);
				return true;
		}});
		
		mesh1.addChild(new AnimationFaceToCamera(s.getCamera(), 0.5f));
		mesh1.setScale(new Vec(2, 2, 2));
		retVal.add(MarkerObjectFactory.createTimeoutMarker(s, 5, mesh1));
		
		
		return retVal;
		
	}
	
	

}
