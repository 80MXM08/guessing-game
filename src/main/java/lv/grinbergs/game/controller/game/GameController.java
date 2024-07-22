package lv.grinbergs.game.controller.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.grinbergs.game.model.Game;
import lv.grinbergs.game.model.State;
import lv.grinbergs.game.service.GameService;
import lv.grinbergs.game.service.PlayerService;

@Controller
public class GameController {
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;

	@PostMapping("/game")
	public String guess(@RequestParam Long gameId, @ModelAttribute GuessForm guessForm, Model model) {
		Game game = gameService.findGameById(gameId);
		gameService.guess(game, convertStringListToCharList(guessForm.getGuess()));
		model.addAttribute("game", game);
		model.addAttribute("guessForm", new GuessForm());
		return game.getState() == State.PLAYING ? "game" : "gameover";
	}

	@GetMapping("/gameover")
	public String gameOver(@RequestParam Long gameId, Model model) {
		Game game = gameService.findGameById(gameId);
		model.addAttribute("game", game);
		return "gameover";
	}

	private List<Character> convertStringListToCharList(List<String> stringList) {
		return stringList.stream().map(string -> string.charAt(0)).toList();
	}
}