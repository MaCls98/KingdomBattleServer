package model;

import java.util.ArrayList;

public class GameManager {

	private ArrayList<Player> players;
	private ArrayList<Shoot> shoots;
	private boolean isWorking;

	public GameManager() throws InterruptedException {
		shoots = new ArrayList<>();
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void validateGame() {
		moveShoots();
	}

	private void moveShoots() {
		ArrayList<Player> ps = players;
		if (shoots.size() > 0) {
			for (Shoot shoot : shoots) {
				for (Player player : ps) {
					if (shoot.isActive()) {
						if (shoot.validateImpact(player)) {
							player.calculateAttack(shoot.getDamage());
							shoot.setActive(false);
						}
					}
				}
			}
		}
	}

	public void addShoot(Shoot shoot) {
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
