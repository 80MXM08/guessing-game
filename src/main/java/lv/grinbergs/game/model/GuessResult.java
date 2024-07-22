package lv.grinbergs.game.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GuessResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private List<Character> guess;
	private int matches;
	private int partialMatches;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Character> getGuess() {
		return guess;
	}

	public void setGuess(List<Character> guess) {
		this.guess = guess;
	}

	public int getMatches() {
		return matches;
	}

	public void setMatches(int matches) {
		this.matches = matches;
	}

	public int getPartialMatches() {
		return partialMatches;
	}

	public void setPartialMatches(int partialMatches) {
		this.partialMatches = partialMatches;
	}
}