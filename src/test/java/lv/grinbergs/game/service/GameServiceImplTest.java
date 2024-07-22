package lv.grinbergs.game.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import lv.grinbergs.game.business.rng.SecretNumberGenerator;
import lv.grinbergs.game.model.Game;
import lv.grinbergs.game.model.GuessResult;
import lv.grinbergs.game.model.Player;
import lv.grinbergs.game.model.State;
import lv.grinbergs.game.repository.GameRepository;

class GameServiceImplTest {

	@Mock
	private PlayerService playerService;

	@Mock
	private GameRepository gameRepository;

	@Mock
	private GuessResultService guessResultService;

	@Mock
	private Environment environment;

	@InjectMocks
	private GameServiceImpl gameService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(environment.getProperty("game.numberLength", "4")).thenReturn("4");
		when(environment.getProperty("game.maxNumberOfTries", "8")).thenReturn("8");
	}

	@Test
	void startsNewGame() {
		Player player = new Player();
		Game game = new Game();
		game.setPlayer(player);
		game.setSecretNumber(SecretNumberGenerator.generate(4));
		game.setMaxAttempts(8);

		when(gameRepository.save(any(Game.class))).thenReturn(game);

		Game newGame = gameService.startNewGame(player);

		verify(gameRepository, times(1)).save(any(Game.class));
		assert newGame.getPlayer() == player;
		assert newGame.getSecretNumber().size() == 4;
		assert newGame.getMaxAttempts() == 8;
	}

	@Test
	void guessesCorrectly() {
		Game game = new Game();
		List<Character> secretNumber = Arrays.asList('1', '2', '3', '4');
		game.setSecretNumber(secretNumber);
		game.setMaxAttempts(8);
		game.setGuessResults(new ArrayList<>());
		Player player = new Player();
		game.setPlayer(player);

		List<Character> guess = Arrays.asList('1', '2', '3', '4');
		GuessResult guessResult = new GuessResult();
		guessResult.setGuess(secretNumber);
		when(guessResultService.create(secretNumber, guess)).thenReturn(guessResult);

		gameService.guess(game, guess);

		verify(playerService, times(1)).update(player, true);
		verify(gameRepository, times(1)).save(game);
		assert game.getState() == State.VICTORY;
		assert game.getGuessResults().size() == 1;
	}

	@Test
	void guessesIncorrectly() {
		Game game = new Game();
		List<Character> secretNumber = Arrays.asList('1', '2', '3', '4');
		game.setSecretNumber(secretNumber);
		game.setMaxAttempts(8);
		game.setGuessResults(new ArrayList<>());
		Player player = new Player();
		game.setPlayer(player);

		List<Character> guess = Arrays.asList('1', '2', '3', '5');
		GuessResult guessResult = new GuessResult();
		guessResult.setGuess(guess);
		when(guessResultService.create(secretNumber, guess)).thenReturn(guessResult);

		gameService.guess(game, guess);

		verifyNoInteractions(playerService);
		verify(gameRepository, times(1)).save(game);
		assert game.getState() == State.PLAYING;
		assert game.getGuessResults().size() == 1;
	}

	@Test
	void guessesIncorrectlyAndLooses() {
		Game game = new Game();
		List<Character> secretNumber = Arrays.asList('1', '2', '3', '4');
		game.setSecretNumber(secretNumber);
		game.setMaxAttempts(1);
		game.setGuessResults(new ArrayList<>());
		Player player = new Player();
		game.setPlayer(player);

		List<Character> guess = Arrays.asList('1', '2', '3', '5');
		GuessResult guessResult = new GuessResult();
		guessResult.setGuess(guess);
		when(guessResultService.create(secretNumber, guess)).thenReturn(guessResult);

		gameService.guess(game, guess);

		verify(playerService, times(1)).update(player, false);
		verify(gameRepository, times(1)).save(game);
		assert game.getState() == State.OUT_OF_TRIES;
		assert game.getGuessResults().size() == 1;
	}
}