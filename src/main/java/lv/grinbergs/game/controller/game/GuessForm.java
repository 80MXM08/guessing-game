package lv.grinbergs.game.controller.game;

import java.util.ArrayList;
import java.util.List;

public class GuessForm {
	private List<String> guess;

	public GuessForm() {
		this.guess = new ArrayList<>();
	}

	public List<String> getGuess() {
		return guess;
	}

	public void setGuess(List<String> guess) {
		this.guess = guess;
	}
}