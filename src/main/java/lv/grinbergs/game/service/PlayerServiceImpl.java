package lv.grinbergs.game.service;

import static java.util.Objects.nonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.grinbergs.game.model.Player;
import lv.grinbergs.game.repository.PlayerRepository;

@Service
class PlayerServiceImpl implements PlayerService {
	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public Player getOrCreate(String name) {
		Player player = playerRepository.findByName(name);
		return nonNull(player) ? player : create(name);
	}

	@Override
	public void update(Player player, boolean correctGuess) {
		if (correctGuess) {
			player.setCorrectGuesses(player.getCorrectGuesses() + 1);
		}
		player.setGamesPlayed(player.getGamesPlayed() + 1);
		playerRepository.save(player);
	}

	@Override
	public List<Player> findTop(int minGames) {
		return playerRepository.findAll().stream().filter(player -> player.getGamesPlayed() >= minGames)
				.sorted(Comparator.comparingDouble((Player p) -> (double) p.getCorrectGuesses() / p.getGamesPlayed())
						.reversed().thenComparingInt(Player::getGamesPlayed)).collect(Collectors.toList());
	}

	private Player create(String name) {
		Player player = new Player();
		player.setName(name);
		player.setGamesPlayed(0);
		player.setCorrectGuesses(0);
		playerRepository.save(player);
		return player;
	}
}
