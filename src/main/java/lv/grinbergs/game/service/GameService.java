package lv.grinbergs.game.service;

import java.util.List;

import lv.grinbergs.game.model.Game;
import lv.grinbergs.game.model.Player;

public interface GameService {
	Game startNewGame(Player player);

	Game findGameById(Long id);

	void guess(Game game, List<Character> guess);
}
