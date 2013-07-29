/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		label.setFont(MESSAGE_FONT);
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		remove(label);
		label.setLabel(msg);
		double length = label.getWidth();
		double x = getWidth() / 2 - length / 2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(label, x, y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		
		if (profile == null) return;
		
		//Adds name to canvas
		GLabel name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + name.getHeight();
		add(name, x, y);
		
		//Adds picture to canvas if it exists
		if (profile.getImage() != null) {
			GImage picture = profile.getImage();
			picture.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			y += IMAGE_MARGIN;
			add(picture, x, y);
		} else {
			y += IMAGE_MARGIN;
			GRect frame = new GRect(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			x = LEFT_MARGIN + IMAGE_WIDTH / 2 - noImage.getWidth() / 2;
			y += (IMAGE_HEIGHT + noImage.getHeight()) / 2;
			add(frame);
			add(noImage, x, y);
		}
		
		//Adds the status to canvas if it exists
		String msg;
		if (profile.getStatus().equals("")) {
			msg = "No current status.";
		} else {
			msg = profile.getName() + " is " + profile.getStatus() + ".";
		}
		
		GLabel status = new GLabel(msg);
		status.setFont(PROFILE_STATUS_FONT);
		x = LEFT_MARGIN;
		y = TOP_MARGIN + name.getHeight() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getHeight();
		add(status, x, y);
		
		//Adds friends list to canvas
		GLabel friendTitle = new GLabel("Friends:");
		friendTitle.setFont(PROFILE_FRIEND_LABEL_FONT);
		x = getWidth() / 2;
		y = TOP_MARGIN + name.getHeight() + IMAGE_MARGIN;
		add(friendTitle, x, y);
		
		Iterator<String> friendList = profile.getFriends();
		while (friendList.hasNext()) {
			GLabel friend = new GLabel(friendList.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			y += friend.getHeight();
			add(friend, x, y);
		}
		
	}

	private GLabel label = new GLabel("Hello There!");
	
}
