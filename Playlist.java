/*  The class represents a Playlist of podcast episodes, where each
/*  episode is implemented as an object of type Episode. A user navigating
/*  a Playlist should be able to move between songs using next or previous references.
/*
/*  To enable flexible navigation, the Playlist is implemented as
/*  a Doubly Linked List where each episode has a link to both the
/*  next and the prev episodes in the list.
*/
import java.util.*;

public class Playlist {
	private Episode head; //points to first episode in the playlist
	private int size; //keeps track of the number of episodes in the playlist

	public Playlist() {
		head = null; //constructor initializes empty playlist
		size = 0; 
	}

	public boolean isEmpty() { //checks if playlist is empty
		return head == null;
	}

	// Ensure that "size" is updated properly in other methods, to always
	// reflect the correct number of episodes in the current Playlist
	public int getSize() {
		return size; //returns the current number of episodes in a playlist
	}

	// Our implementation of toString() displays the Playlist forward,
	// starting at the first episode (i.e. head) and ending at the last episode,
	// while utilizing the "next" reference in each episode
	@Override
	public String toString()
	{
		String output = "[HEAD] ";
		Episode current = head;
		if ( ! isEmpty() ) {  //checking if playlist is not empty
			while( current.next != null ) { //iterates through each episode until it reaches the last oen
				output += current + " -> "; //preparing to print out "HEAD" ep1 --> //and each time it adds a new episode
				current = current.next; //moves to the next episode
			}
			output += current + " [END]\n"; 
		}
		else {
			output += " [END]\n";
		}
		return output;
	}


	// This method displays the Playlist backward, starting at
	// the last episode and ending at the first episode (i.e. head),
	// while utilizing the "prev" reference in each episode
	public String toReverseString() {
		String output = "[END] ";
		Episode current = head;
		if( ! isEmpty() ) { //if the playlist has episodes in it
			while(current.next != null) //keep traversing until the enxt episode is null
				current = current.next; //points to the last episode
				// current is now pointing to last node/episode in the playlist

			
			while( current.prev != null ) { //while the previous episode is not null
				output += current + " -> "; //since it points to the last episode in the first while loop it keeps updating the print statement starting from the END to the HEAD
				current = current.prev;//current is now the previous episode
			}
			output += current + " [HEAD]\n"; // after printing out all the episodes it'll print out "HEAD" in the final statement
		}
		else {
			output += " [HEAD]\n";
		}
		return output;
	}


	/**************************************************************/
	//(Add/Delete Operations)

	public void addFirst( String title, double duration ){ //adds at the beginning of the linkedlist
		Episode next =head; 
		Episode prev = null; //because theres nothing 
		Episode newEpisode = new Episode(title, duration, next, prev); //creating a new episode and passing the parameters
		if(isEmpty()){ 
			head = newEpisode;
		} else{
			newEpisode.next = head; //links the new episode to the head
			head.prev = newEpisode;
			head = newEpisode; //the new episode is now the head
		}
		size++; //have to increment the size

		//new epsiode points to the current head and 
		//then the current head points to the new episode so it can update the new episode to be the head and keep adding new epsiodes to the beginning
	}

	public void addLast( String title, double duration ){
		if(isEmpty()){ 
			Episode next = null; 
			Episode prev = null;
			Episode newEpisode = new Episode(title, duration,next, prev);
			head = newEpisode; //if the playlist is empty the newEpisode is the head and tail
		}else{ 
			Episode next = null; 
			Episode prev = null;
			Episode newEpisode = new Episode(title, duration,next, prev);
	
			Episode currentEpisode = head; //assigning the current episode to be the headn
			while (currentEpisode.next!=null){ 
				currentEpisode = currentEpisode.next; //keep traversing through the playlist until you reach the last episode
			}
			currentEpisode.next = newEpisode; //pointing the next reference to the new Episode
											  //so the end of the linked list really just points to newEpisode
			newEpisode.prev = currentEpisode; //the current episode now gains the previous reference

		}
		size ++; //incrementing size
	}

	 public Episode deleteFirst(){
		Episode deleted = head;
		if(isEmpty()){ 
			throw new NoSuchElementException("Playlist is empty :( ");
            					//if playlist is empty it'll return an error message
		}if(head.next == null){//if theres only 1 song in the playlist it'll delete the head
			head = null;

		}else{ //if there are multiple songs in the playlist it'll delete the head
			head = head.next; 
			head.prev = null;
		}
		size--; //decrementing the size
		return deleted;
	 }

	public Episode deleteLast(){
		Episode deleted = null; //null because it'll gain reference to an episode when returned at the end
		Episode current = head; 
		if(isEmpty()){ 
			throw new NoSuchElementException("Playlist is empty :( ");
            //if playlist is empty it'll return an error message

		}if(head.next == null){//if theres only 1 song in the playlist it'll delete the head which is basically also the tail
			deleted = head; //before I delete it I have to make sure that it had a reference as to what the last element was, for when i return it at the end
			head = null;

		}else{ //if there are multiple songs in the playlist it'll delete the last episode
			while(current.next!= null){ 
				current = current.next;
			}
			deleted = current; //before deleting current(which is the last episode) we are creating a reference variable for it //current is now the last episode
			current.prev.next = null; //the episode before the last episode points to its next reference which will be null, so it effectively removes the last episode
		}
		size--; //decrementing the size
		return deleted;
	}

	public Episode deleteEpisode(String title){
		if(isEmpty()){
			throw new NoSuchElementException("Playlist is empty:(");
		}//end if	

		Episode current = head;
		Episode deleted = null; 

		if(head.title.equals(title)){ //checking if the first episode matched the title
			deleted = head; 
			head = head.next;
			if(head!=null){ //head.next could be null but if its not null then it'll assign the episode before it to be null 
			head.prev = null; //updating the previous position to null to show that that is the first episode 
			}
			size--;
			return deleted;
			
		}//end if
		
		boolean found = true;
		while(current.next != null){ //this is for if there are multiple episdoes in a playlist
			current = current.next;
			if(current.title.equals(title)){ 
				deleted = current; 
				if(current.prev != null){
				current.prev.next = current.next;//current points to the previous position 
				}
				if(current.next!=null){
				current.next.prev = current.prev; // current then points to the postion after current which links the two and gets rid of the middle 
				}
			}//end if

				size--;
				found = true;
				return deleted;
			}//end while
			if(!found){
				throw new NoSuchElementException("Title not found :(");// if it iterates through playlist and doesnt find title
			}//end if
			return null;
	}




	/***************************************************************/
	//(Sorting the Playlist using MergeSort)

	 public Episode merge(Episode a, Episode b){
		//base cases : if either list is empty it'll return the other list's content
		if(a == null){
			return b;
		}
		if(b == null){
			return a;
		}
		Episode outcome = head;
		if(a.compareTo(b) <= 0){  //means if (a) is less than or equal to (b) 
			outcome = a; //if true it'll return a because it'll be ordered from smallest to largest
			outcome.next = merge(a.next, b); //compares the next position of with the first position of b
		}else{
			outcome =b; //else it'll notify that b was actually the smallest number 
			outcome.next = merge(a,b.next);//keep recursing untill all is merged
		}
		return outcome; 
	 }


	// Finds the middle episode of the list that begins at the passed node reference
	private Episode getMiddleEpisode(Episode node) {
		if(node == null) return node;
		Episode slow = node;
		Episode fast = node;
	    while(fast.next != null && fast.next.next != null) {
	         slow = slow.next;
	         fast = fast.next.next;
	    }
	    return slow;
	 }

    // MergeSort starting point
    public void mergeSort() {
	     if( isEmpty() ) throw new RuntimeException("Cannot sort empty list.");
	     head = sort(head);
    }

    // Recursively splits the list starting at a given node reference
	public Episode sort(Episode node) {
	     if(node == null || node.next == null)
	         return node;
	     Episode middle = getMiddleEpisode(node); //get the middle of the list
	     Episode left_head = node;
	     Episode right_head = middle.next;

	     // split the list into two halves:
	     if(right_head != null) right_head.prev = null;
	     middle.next = null;

	     Episode left = sort(left_head);
	     Episode right = sort(right_head);
	     return merge(left, right);
	 }


} // End of Playlist class