package lv.grinbergs.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import lv.grinbergs.game.business.rng.SecretNumberGenerator;
import lv.grinbergs.game.model.Game;
import lv.grinbergs.game.model.GuessResult;
import lv.grinbergs.game.model.Player;
import lv.grinbergs.game.model.State;
import lv.grinbergs.game.repository.GameRepository;

@Service
class GameServiceImpl implements GameService {
	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private GuessResultService guessResultService;

	@Autowired
	private Environment environment;

	@Override
	public Game startNewGame(Player player) {
		Game game = new Game();
		game.setPlayer(player);
		game.setSecretNumber(SecretNumberGenerator.generate(getSecretNumberLength()));
		game.setMaxAttempts(getMaxAttempts());
		gameRepository.save(game);
		return game;
	}

	@Override
	public Game findGameById(Long id) {
		return gameRepository.getReferenceById(id);
	}

	@Override
	public void guess(Game game, List<Character> guess) {
		List<Character> secretNumber = game.getSecretNumber();
		GuessResult guessResult = guessResultService.create(secretNumber, guess);
		game.getGuessResults().add(guessResult);
		if (secretNumber.equals(guessResult.getGuess())) {
			playerService.update(game.getPlayer(), true);
			game.setState(State.VICTORY);
		} else if (game.getGuessResults().size() >= game.getMaxAttempts()) {
			playerService.update(game.getPlayer(), false);
			game.setState(State.OUT_OF_TRIES);
		}
		gameRepository.save(game);
	}

	private int getSecretNumberLength() {
		return Integer.parseInt(environment.getProperty("game.numberLength", "4"));
	}

	private int getMaxAttempts() {
		return Integer.parseInt(environment.getProperty("game.maxNumberOfTries", "8"));
	}
}
