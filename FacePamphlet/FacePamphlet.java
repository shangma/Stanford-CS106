/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends /*Console*/Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		/* Adds the FacePamphletCanvas to the application window. */
		add(canvas);
		
		canvas.showMessage("hello there!");
		
		/* Change Status section */
		status = new JTextField(TEXT_FIELD_SIZE);
		add(status, WEST);
		status.addActionListener(this);
		add(new JButton("Change Status"), WEST);
		///////////////////////////
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		/* Change Picture section */
		picture = new JTextField(TEXT_FIELD_SIZE);
		add(picture, WEST);
		picture.addActionListener(this);
		add(new JButton("Change Picture"), WEST);
		////////////////////////////
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		/* Add Friend section */
		friend = new JTextField(TEXT_FIELD_SIZE);
		add(friend, WEST);
		friend.addActionListener(this);
		add(new JButton("Add Friend"), WEST);
		////////////////////////////
		
		add(new JLabel("Name"),NORTH);
		
		/* Profile Actions section */
		name = new JTextField(TEXT_FIELD_SIZE);
		add(name, NORTH);
		name.addActionListener(this);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		/////////////////////////////
		
		addActionListeners();
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	
    	///// Status update actions /////
    	if (e.getSource() == status) {
    		changeProfile();
    	}
    	
    	if (e.getActionCommand().equals("Change Status")) {
    		changeProfile();
		}
    	//////////////////////////////////
		
    	///// Picture update actions /////
    	if (e.getSource() == picture) {
    		changePicture();
    	}
    	
		if (e.getActionCommand().equals("Change Picture")) {
			changePicture();
		}
		///////////////////////////////////
		
		///// Friend addition actions /////
		if (e.getSource() == friend) {
			addFriend();
		}
		
		if (e.getActionCommand().equals("Add Friend")) {
			addFriend();
		}
		///////////////////////////////////
		
		///// Profile actions /////
		/* Adding profiles */
		if (e.getSource() == name) {
			addProfile();
		}
		
		if (e.getActionCommand().equals("Add")) {
			addProfile();
		}
		
		/* Deleting profiles */
		if (e.getActionCommand().equals("Delete")) {
			deleteProfile();
		}
		
		/* Looking up profiles */
		if (e.getActionCommand().equals("Lookup")) {
			lookUpProfile();
		}
		////////////////////////////////////////////////
	}
    
    
    /**
     * This method prints out a message saying what the current profile is. 
     * If there is a current profile then it will print out the toString() 
     * method for that profile. If the current profile is null then it will
     * print out that there is no current profile.
     */
    private void currentProfileMessage() {
    	if (currentProfile != null) {
    		println("--> Current profile: " + currentProfile.toString());
    		canvas.displayProfile(currentProfile);
    	} else {
    		println("--> No current Profile");
    	}
    }
    
    
    /**
     *  This method changes the status of the current profile. If the current
     *  profile is null it prompts the user to select a profile.
     */
    private void changeProfile() {
    	if (currentProfile != null) {
    		String statusUpdate = status.getText();
    		currentProfile.setStatus(statusUpdate);
    		    		
    		canvas.displayProfile(currentProfile);
    		canvas.showMessage("Status updated to " + currentProfile.getStatus());
		} else {
			canvas.showMessage("Please select a profile to change status.");
		}
    }
    
    
    /**
     * This method changes the profile picture of the current profile. If the
     * current profile is null then it prompts the user to select a profile.
     */
    private void changePicture() {
    	if (currentProfile != null) {
    		String fileName = picture.getText();
    		
    		GImage image = null; 
    		try {
    			image = new GImage(fileName);
    			currentProfile.setImage(image);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Image set to " + fileName + ".");
    		} catch (ErrorException ex) {
    			canvas.showMessage("Image does not exist.");
    		}
   		} else {
			canvas.showMessage("Please select a profile to change picture");
		}
    }
    
    
    /**
     * This method adds friends to the current profile's friend list if such a
     * friend exists. If the current profile is null then it prompts the user to 
     * select a profile. If the friend does not exist then a message will print
     * out stating such. If the friend is already a friend then a message will 
     * print out stating such. If the friend exists and is NOT already a friend
     * then the friend will be added to the current profile's friend list AND the
     * current profile will be added onto the friends friend list.
     */
    private void addFriend() {
    	if (currentProfile != null) {
    		String newFriend = friend.getText();
    		
    		if (profiles.containsProfile(newFriend)) {
    			boolean madeFriend = currentProfile.addFriend(newFriend);
    			if (madeFriend){
    				FacePamphletProfile friend = profiles.getProfile(newFriend);
    				friend.addFriend(currentProfile.getName());
    			
    				canvas.displayProfile(currentProfile);
    				canvas.showMessage(friend.getName() + " added as a friend");
    			} else {
    				canvas.showMessage(newFriend + " was already a friend.");
    			}
       		} else {
    			canvas.showMessage(newFriend + " does not have a profile");
    		}
    	} else {
    		canvas.showMessage("Please select a profile to change picture");
    	}
    }
    
    
    /**
     * This method adds profiles into the FacePamphletDatabase. If the profile does not exist
     * then a new one is created. If the profile does exist then a message is printed out 
     * stating such.
     */
    private void addProfile() {
    	String profileName = name.getText();
		
		if (!profiles.containsProfile(profileName)) {
			currentProfile = new FacePamphletProfile(profileName);
			profiles.addProfile( currentProfile );
			
			canvas.displayProfile(currentProfile);
			canvas.showMessage("New profile: " + currentProfile.toString());
		}else {
			currentProfile = profiles.getProfile(profileName);
			
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile for " + currentProfile.getName() + " already exists: " + currentProfile.toString());
		}
    }
    
    
    /**
     * This method deletes profiles from the FacePamphletDatabase.  If the profile does not exist
     * then a message is printed out stating such.  If the profile does exist then the profile is
     * removed from the database and is also removed from all friend lists.
     */
    private void deleteProfile() {
    	String profileName = name.getText();
		
		if (profiles.containsProfile(profileName)) {
			profiles.deleteProfile(profileName);
			currentProfile = null;
			
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile of " + profileName + " deleted");
		} else {
			currentProfile = null;
			
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Profile with name " + profileName + " does not exist");
		}
    }
    
    
    /**
     * This method sets the currentProfile to the selected profile if it exists. If it does not exist 
     * then a message will be printed out stating such.
     */
    private void lookUpProfile() {
    	String profileName = name.getText();
		if (profiles.containsProfile(profileName)) {
			currentProfile = profiles.getProfile(profileName);
			canvas.displayProfile(currentProfile);
			canvas.showMessage(currentProfile.toString());
		} else {
			currentProfile = null;
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Lookup: profile with name " + profileName + " does not exist");
		}
    }
    
    
    /* Private instance variables */
	private JTextField status;
	private JTextField picture;
	private JTextField friend;
	private JTextField name;
	FacePamphletDatabase profiles = new FacePamphletDatabase();
	FacePamphletProfile currentProfile = null;
	FacePamphletCanvas canvas = new FacePamphletCanvas();
}
