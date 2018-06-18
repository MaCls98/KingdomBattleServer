package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Player;

public class JSonPlayer {
	
	private ArrayList<Player> players;
	
	public JSonPlayer() {
		players = new ArrayList<>();
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	@SuppressWarnings("unchecked")
	public void writePlayersJSon() throws IOException{
		JSONObject root = new JSONObject();
		JSONObject dataObj = new JSONObject();
		JSONArray playersArray = new JSONArray();
		
		for (Player player: players) {
			JSONObject playerObj = new JSONObject();
			playerObj.put("name", player.getName());
			playerObj.put("xAxis", player.getxAxis());
			playerObj.put("yAxis", player.getyAxis());
			playerObj.put("direction", player.getDirection());
			playerObj.put("health", player.getHealth());
			playerObj.put("attack", player.getAttack());
			playersArray.add(playerObj);
		}
		dataObj.put("players", playersArray);
		root.put("data", dataObj);
		
		FileWriter writer = new FileWriter(new File("./players.json"));
		writer.write(root.toJSONString());
		writer.close();
	}
}
