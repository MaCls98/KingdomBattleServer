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
		checkWinner();
	}

	private void checkWinner() {
		ArrayList<Player> ps = players;
		int i = 0;
		for (Player player : ps) {
			if (!player.isAlive()) {
				i++;
			}
		}
		if (i == 1) {
			for (Player player : players) {
				if (player.isAlive()) {
					player.setWinner(true);
				}
			}
		}
	}

	private void moveShoots() {
		ArrayList<Player> ps = players;
		if (shoots.size() > 0) {
			for (Shoot shoot : shoots) {
				for (Player player : ps) {
					if (shoot.isActive()) {
						if (!shoot.getName().equals(player.getName())) {
							if (shoot.validateImpact(player)) {
								if (player.isAlive()) {
									player.calculateAttack(shoot.getDamage());
									shoot.setActive(false);
								}
							}
						}
					}
					if (player.getHealth() <= 0) {
						player.setAlive(false);
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
