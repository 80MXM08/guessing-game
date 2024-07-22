package lv.grinbergs.game.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.grinbergs.game.model.GuessResult;
import lv.grinbergs.game.repository.GuessResultRepository;

@Service
class GuessResultServiceImpl implements GuessResultService {

	@Autowired
	private GuessResultRepository guessResultRepository;

	@Override
	public GuessResult create(List<Character> secretNumber, List<Character> guess) {
		int m = 0, p = 0;
		for (int i = 0; i < secretNumber.size(); i++) {
			if (Objects.equals(guess.get(i), secretNumber.get(i))) {
				m++;
			} else if (secretNumber.contains(guess.get(i))) {
				p++;
			}
		}
		return createGuess(guess, m, p);
	}

	private GuessResult createGuess(List<Character> guess, int matches, int partialMatches) {
		GuessResult gameInput = new GuessResult();
		gameInput.setGuess(guess);
		gameInput.setMatches(matches);
		gameInput.setPartialMatches(partialMatches);
		guessResultRepository.save(gameInput);
		return gameInput;
	}
}
