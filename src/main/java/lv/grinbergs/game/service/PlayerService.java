package lv.grinbergs.game.service;

import java.util.List;

import lv.grinbergs.game.model.Player;

public interface PlayerService {
	Player getOrCreate(String name);

	void update(Player player, boolean correctGuess);

	List<Player> findTop(int minGames);
}