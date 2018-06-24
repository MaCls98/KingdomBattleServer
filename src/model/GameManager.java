package model;

import java.util.ArrayList;

public class GameManager extends Thread{
	
	private ArrayList<Player> players;
	private ArrayList<Shoot> shoots;
	private boolean isWorking;
	
	public GameManager() throws InterruptedException{
		shoots = new ArrayList<>();
		sleep(300);
		start();
	}
	
	@Override
	public void run() {
		while (true) {
			isWorking = true;
			validateGame();
			isWorking = false;
		}
	}
	
	public boolean isWorking() {
		return isWorking;
	}
	
	public void validateGame(){
		moveShoots();
	}

	private void moveShoots() {
		if (players != null) {
			for (Player player : players) {
				validateShoots(player);
			}
		}
	}

	private void validateShoots(Player player) {
		if (shoots != null) {
			for (Shoot shoot : shoots) {
				if (shoot.isActive()) {
					shoot.move();
					System.out.println(shoot);
					if (Math.abs(shoot.getX() - player.getxAxis()) < 15 && Math.abs(shoot.getY() - player.getyAxis()) < 15) {
						shoot.setActive(false);
						player.calculateAttack(shoot.getDamage());
					}
				}
			}
		}
	}
	
	public void addShoot(Shoot shoot){
		shoots.add(shoot);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Shoot> getShoots() {
		return shoots;
	}

	public void setShoots(ArrayList<Shoot> shoots) {
		this.shoots = shoots;
	}
}
