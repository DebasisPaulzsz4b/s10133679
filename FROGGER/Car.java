/**
 * Car - these are the cars that frogger will have to dodge in order to get to his flower and back
 * 
 * @author Colin MacDougall unless otherwise specified
 *
 */


public class Car extends Player implements GameListener {
	/**
	 * Constructs Car based on the X,Y location on the map
	 * @param x
	 * @param y
	 */
	public Car(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructs Car based not only on X,Y locations, but also by the direction the Car is facing 
	 * and how many 'lives' the car has (which will probably be used for different images)
	 * An image depicting the car is also set.
	 * @param x
	 * @param y
	 * @param direction
	 * @param numOfLives - will possibly be used later for adding different images to cars
	 */
	public Car(int x, int y, Direction direction, int numOfLives) {
		super(x,y,direction,numOfLives);
		putImage();
	}

	/**
	 * Listens for events from game
	 * The cars specifically listen for a MoveCars event that will make all the cars move
	 * 
	 * @param e
	 */
	public void onEvent(GameEvent e) {

		if(e.getSource().equals("movement") && e.getGameValue() instanceof FroggerGame) {
			moveCars(((Game) e.getGameValue()).getMap());
		}
	}
	/**
	 * This function dictates the movement of the Cars
	 * They simply move in the direction they are facing
	 * 
	 * @param map - This is the map they are moving on
	 */
	public void moveCars(Map map){
		//if car is moving right, but there is a wall in it's path, loop it back to the other side
		//The purpose of removing the mappable object and re-mapping it is to eliminate visual bugs
		if(this.getDirection() == Direction.RIGHT){
			if(map.isWall(this.getX()+1,this.getY()) == true){
				map.removeMappable(this.getX(), this.getY());
				this.setX(0);
				map.addMappable(this);
			}
		}
		//if car is moving left, but there is a wall in it's path, loop it back to the other side
		if(this.getDirection() == Direction.LEFT){
			if(map.isWall(this.getX()-1,this.getY()) == true){
				map.removeMappable(this.getX(), this.getY());
				this.setX(18);
				map.addMappable(this);
			}
		}
		this.updateLocation(map); //actually move the car
	}

	
	//these aren't used, but need to be here
	@Override
	public void die(Map map) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void spawn(Map map) {
		// TODO Auto-generated method stub
	}
	
	public void putImage(){
		switch (this.getNumOflives()){
		case 1: //cars with lives of 1 get red car in the RIGHT direction
		case 2: //cars with lives of 2 get red car in the LEFT direction
			setImage("FROGGER/car"+this.getNumOflives()+".png");
			break;
		case 3:
		case 4://cars with lives from 3-6 get the appropriate piece of truck in the RIGHT direction
		case 5:
		case 6:
			setImage("FROGGER/truckR"+(this.getNumOflives()-2)+".png");
			break;
		case 7:
		case 8://cars with lives from 7-10 get the appropriate piece of truck in the LEFT direction
		case 9:
		case 10:
			setImage("FROGGER/truckL"+(this.getNumOflives()-6)+".png");
			break;
		}
	}
}