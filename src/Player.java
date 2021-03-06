
/**
 * @author  imran
 */
public abstract class Player extends Mappable {
	/**
	 * @uml.property  name="direction"
	 * @uml.associationEnd  
	 */
	private Direction direction;
	/**
	 * @uml.property  name="numOflives"
	 */
	private int numOflives;
	/**
	 * General purpose constructor for Player. Sets direction to Direction.LEFT and numOfLives to 1.
	 * @param x Desired X position of Player
	 * @param y Desired Y position of Player
	 */
	public Player(int x, int y){
		super(x, y);
		this.direction = Direction.LEFT;
		this.numOflives = 1;
	}
	/**
	 * A more specific constructor for Player. Allows direction and numOfLives to be set to desired values.
	 * @param x Desired X position of Player
	 * @param y Desired Y position of Player
	 * @param direction Desired direction of player ( UP, DOWN, LEFT, RIGHT )
	 * @param numOflives desired number of lives
	 */
	public Player(int x, int y, Direction direction, int numOflives) {
		super(x, y);
		this.direction = direction;
		this.numOflives = numOflives;
		
	}
	
	/**
	 * @return  Current direction of Player
	 * @uml.property  name="direction"
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Change the current direction of Player
	 * @param direction  Either UP, DOWN, LEFT or RIGHT
	 * @uml.property  name="direction"
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * @return  Number of lives of the Player
	 * @uml.property  name="numOflives"
	 */
	public int getNumOflives() {
		return numOflives;
	}
	
	/**
	 * Change the current number of lives of the Player
	 * @param numOflives  number of lives desired.
	 * @uml.property  name="numOflives"
	 */
	public void setNumOflives(int numOflives) {
		this.numOflives = numOflives;
	}
	/**
	 * Moves the Player in its current direction.
	 * @param Map current map the Player is on
	 * @return true if the move was successful, false otherwise.
	 */
	public boolean updateLocation(Map map){
		if(map.isOutOfBounds(getX(), getY())) return false;
		switch(this.direction){
			case UP:
				if(!map.isWall(this.getX(), this.getY() - 1)){
					map.removeMappable(this.getX(), this.getY());
					this.setY(this.getY() - 1);
					return map.addMappable(this);
				}
				return false;
			case LEFT:
				if(!map.isWall(this.getX() - 1, this.getY())){
					map.removeMappable(this.getX(), this.getY());
					this.setX(this.getX() - 1);
					return map.addMappable(this);
				}
				return false;
			case DOWN:
				if(!map.isWall(this.getX(), this.getY() + 1)){
					map.removeMappable(this.getX(), this.getY());
					this.setY(this.getY() + 1);
					return map.addMappable(this);
				}
				return false;
			case RIGHT:
				if(!map.isWall(this.getX() + 1, this.getY())){
					map.removeMappable(this.getX(), this.getY());
					this.setX(this.getX() + 1);
					return map.addMappable(this);
				}
				return false;
		}
		//Random return to pacify Eclipse
		return false;
	}

	public abstract void spawn(Map map);
	public abstract void die(Map map);

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (numOflives != other.numOflives)
			return false;
		if (getX() != other.getX())
			return false;
		if (getY() != other.getY())
			return false;
		return true;
	}
	
	
}
