package lv.grinbergs.game.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private State state;
	private List<Character> secretNumber;

	@OneToMany
	private List<GuessResult> guessResults;
	private int maxAttempts;

	@ManyToOne
	private Player player;

	public Game() {
		this.state = State.PLAYING;
		this.guessResults = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Character> getSecretNumber() {
		return secretNumber;
	}

	public void setSecretNumber(List<Character> secretNumber) {
		this.secretNumber = secretNumber;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public List<GuessResult> getGuessResults() {
		return guessResults;
	}

	public void setGuessResults(List<GuessResult> guessResults) {
		this.guessResults = guessResults;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}