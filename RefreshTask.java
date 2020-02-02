package application;

import java.util.ArrayList;

/**
 * This thread is responsible for handling if a note was not hit.
 * When a note passes the board without being hit, it will count
 * as a "miss" and it will update the stats accordingly.
 * 
 * @author Evan Argenal
 * 
 */
class RefreshTask extends Thread {
	
	private volatile boolean shutdown;
	ArrayList<NewNote> arrayNotes;
    
	/**
	 * constructor for the thread
	 * 
	 * @param notes, the thread will check these notes every 5 milliseconds
	 */
	public RefreshTask(ArrayList<NewNote> notes) {
		this.arrayNotes = notes;
	}
	
	/**
	 * Everything in this method is the thread.
	 * On this separate thread, it will consistently
	 * check the y-cord of every note to see if it's
	 * a "miss" from not hitting it on time.
	 */
	public void run() {
    	while(!shutdown) {
    		for(NewNote temp : arrayNotes) {
    			if(temp.note.getTranslateY() > 760 && temp.getID() >= 0 && !temp.getNoteHit()) {
    				temp.checkPass();
    				temp.setID(-10);
    				temp.setNoteHit(true);
    			}
    		}
    		// to prevent lag, only repeat the thread every 5 milliseconds instead
    		try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("There was a problem lmao.");
			}
    	}
    }
	
    /**
     * To shut down the thread when user hits "escape" key.
     */
    public void ShutDown() {
    	shutdown = true;
    }
}