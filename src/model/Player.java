package model;

public class Player {
	
	private String name;
	private int xAxis;
	private int yAxis;
	private int direction;
	private int health;
	private int attack;

	public Player(String name, int xAxis, int yAxis, int direction, int health, int attack) {
		super();
		this.name = name;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.direction = direction;
		this.health = health;
		this.attack = attack;
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