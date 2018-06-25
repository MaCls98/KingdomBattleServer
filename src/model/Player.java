package model;

public class Player {
	
	private String name;
	private int xAxis;
	private int yAxis;
	private int direction;
	private static int health;
	private int attack;
	private int contacts;

	public Player(String name, int xAxis, int yAxis, int direction, int health, int attack) {
		super();
		this.name = name;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.direction = direction;
		Player.health = health;
		this.attack = attack;
		contacts = 0;
	}
	
	public int getContacts() {
		return contacts;
	}
	
	public void setContacts(int contacts) {
		contacts = contacts + 1;
	}
	
	public void calculateAttack(int damage){
		health = health - damage;
	}

	public String getName() {
		return name;
	}

	public int getxAxis() {
		return xAxis;
	}

	public int getyAxis() {
		return yAxis;
	}

	public int getDirection() {
		return direction;
	}

	public int getHealth() {
		return health;
	}

	public int getAttack() {
		return attack;
	}

	@Override
	public String toString() {
		return name + "," + direction + "," + xAxis + "," + yAxis + "," + health + "," + attack;
	}
}