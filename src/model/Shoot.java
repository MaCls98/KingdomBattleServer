package model;

public class Shoot {
	
	private String name;
	private int damage;
	private int direction;
	private int x;
	private int y;
	private static int move = 5;
	private boolean isActive;

	public Shoot(int x, int y, int damage, int direction, boolean isActive, String name) {
		this.damage = damage;
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.isActive = true;
		this.name = name;
	}
	
	public void move(){
		calculateScreen();
		if (isActive) {
			switch (direction) {
			case 1:
				moveRight();
				break;
			case 2:
				moveLeft();
				break;
			case 3:
				moveUp();
				break;
			case 4:
				moveDown();
				break;

			default:
				break;
			}
		}
	}
	
	public boolean validateImpact(Player player){
		move();
		if (Math.abs(getX() - player.getxAxis()) < 32 && Math.abs(getY() - player.getyAxis()) < 32) {
			return true;
		}
		return false;
	}
	
	private void calculateScreen(){
		if (x > 850 || y > 650) {
			isActive = false;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void moveDown() {
		y = y + move;
	}

	public void moveUp() {
		y = y - move;
	}

	public void moveLeft() {
		x = x - move;
	}

	private void moveRight() {
		x = x + move;
	}
	
	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return x + "," + y + "," + damage + "," + direction + "," + isActive + "," + name;
	}
}
