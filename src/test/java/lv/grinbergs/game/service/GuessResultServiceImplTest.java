package lv.grinbergs.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lv.grinbergs.game.model.GuessResult;
import lv.grinbergs.game.repository.GuessResultRepository;

class GuessResultServiceImplTest {

	@Mock
	private GuessResultRepository guessResultRepository;

	@InjectMocks
	private GuessResultServiceImpl guessResultService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createsWithExactMatch() {
		List<Character> secretNumber = Arrays.asList('1', '2', '3', '4');
		List<Character> guess = Arrays.asList('1', '2', '3', '4');

		GuessResult result = guessResultService.create(secretNumber, guess);

		assertEquals(4, result.getMatches());
		assertEquals(0, result.getPartialMatches());
		assertEquals(guess, result.getGuess());

		verify(guessResultRepository, times(1)).save(any());
	}

	@Test
	void createsWithPartialMatch() {
		List<Character> secretNumber = Arrays.asList('1', '2', '3', '4');
		List<Character> guess = Arrays.asList('4', '3', '2', '1');

		GuessResult result = guessResultService.create(secretNumber, guess);

		assertEquals(0, result.getMatches());
		assertEquals(4, result.getPartialMatches());
		assertEquals(guess, result.getGuess());

		verify(guessResultRepository, times(1)).save(any());
	}

	@Test
	void createsWithMixedMatch() {
		List<Character> secretNumber = Arrays.asList('1', '2', '3', '4');
		List<Character> guess = Arrays.asList('1', '3', '4', '2');

		GuessResult result = guessResultService.create(secretNumber, guess);

		assertEquals(1, result.getMatches());
		assertEquals(3, result.getPartialMatches());
		assertEquals(guess, result.getGuess());

		verify(guessResultRepository, times(1)).save(any());
	}

	@Test
	void createsWithNoMatch() {
		List<Character> secretNumber = Arrays.asList('1', '2', '3', '4');
		List<Character> guess = Arrays.asList('5', '6', '7', '8');

		GuessResult result = guessResultService.create(secretNumber, guess);

		assertEquals(0, result.getMatches());
		assertEquals(0, result.getPartialMatches());
		assertEquals(guess, result.getGuess());

		verify(guessResultRepository, times(1)).save(any());
	}
}
