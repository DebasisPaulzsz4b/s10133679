import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * This is the class representing the Pacman player.
 * @variable  state - represents the state (Normal or BeastMode) of Pacman
 * @variable  timer - counts down the time left when Pacman is in BeastMode
 */
public class Pacman extends Player implements GameListener, ActionListener{
	/**
	 * @uml.property  name="state"
	 * @uml.associationEnd  
	 */
	private PacmanState state;
	private Timer beastTimer;
	public Pacman(int x, int y) {
		super(x, y);
		beastTimer = new Timer(5000, this);
		beastTimer.stop();
		// TODO Auto-generated constructor stub
	}
	/**
	 * Initializes Pacman and associates the pacman image to the player
	 * @param x
	 * @param y
	 * @param direction
	 * @param numOfLives
	 */
	public Pacman(int x, int y, Direction direction, int numOfLives) {
		super(x,y,direction,numOfLives);
		setImage("PACMAN/pacmanimg.png");
		state = PacmanState.NORMAL;
		beastTimer = new Timer(5000, this);
	}

	/**
	 * This method spawns Pacman on the Map. Pacman will always spawn at location (9,9) on the Map.
	 * @author Alexander Clelland
	 */
	public void spawn(Map map) {
		setX(9);
		setY(9);
		map.addMappable(this);
	}
	/**
	 * Kills Pacman. Removes  existance on the map.
	 * @author Alexander Clelland
	 */
	public void die(Map map) {
		setNumOflives(getNumOflives()-1);
		ArrayList<Mappable> mappableArray = map.getMappable(getX(),getY());
		if (mappableArray == null) return;
		for (int i=mappableArray.size()-1; i >= 0; i--) {
			if(mappableArray.get(i) == this) {
				map.removeMappable(getX(),getY(),i);
				break;
			}
		}
	
	}

	@Override
	/**
	 * This deals with movement affecting Pacman in some way.
	 * If Pacman moves, he can either collide with a little pill, a big pill, or a ghost
	 * @case little pill, score must be increased, the little pill must be removed 
	 * @case big pill, Pacman's state must be changed
	 * @case ghost, must kill or be killed depending on the state of Pacman
	 * @author Alexander Clelland
	 */
	public void onEvent(GameEvent e) {
		
		//MOVEMENT
		if(e.getSource().equals("movement") && e.getGameValue() instanceof PacmanGame) { //if movement has occured
			PacmanGame tempGame = (PacmanGame)e.getGameValue(); //create a temp variable of the game
			
			ArrayList<Mappable> mappables = tempGame.getMap().getMappable(getX(),getY());
			
			for(int i=mappables.size()-1; i>=0; i--) { //go through array to check if pacman is on a LittlePillItem
				Mappable tempMappable = mappables.get(i);
				if(tempMappable instanceof LittlePillItem) {
					tempGame.getMap().removeMappable(getX(),getY(),i);
					tempGame.setScore(tempGame.getScore()+1); //increment score by 1
					tempGame.setPillsLeft(tempGame.getPillsLeft()-1); //decrement pillsLeft by 1
				}
				else if(tempMappable instanceof Ghost) {
					if(state == PacmanState.NORMAL) { //NORMAL
						die(tempGame.getMap());
						spawn(tempGame.getMap());
					}
					else { //BEASTMODE
						((Ghost)tempMappable).die(tempGame.getMap()); //spawn the ghost at start
					}
				}
				else if(tempMappable instanceof BigPillItem) {
					tempGame.getMap().removeMappable(getX(),getY(),i);
					setState(PacmanState.BEASTMODE); //turn on BEASTMODE!
					//change images to beastmode
				}
			}
		}//end of Movement
	}
	
	
	/**
	 * Allows pacman's state to be changed.
	 * @param state  Pacman's new state
	 * @uml.property  name="state"
	 */
	public void setState(PacmanState state) {
		if(state == PacmanState.BEASTMODE){
			beastTimer.start();
		}
		this.state = state;
	}
	/**
	 * @return  Returns pacmans current state
	 * @uml.property  name="state"
	 */
	public PacmanState getState() {
		return state;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		beastTimer.stop();
		this.setState(PacmanState.NORMAL);
	}

}
