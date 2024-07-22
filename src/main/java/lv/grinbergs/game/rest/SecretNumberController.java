package lv.grinbergs.game.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lv.grinbergs.game.service.GameService;

@RestController
public class SecretNumberController {
	@Autowired
	private GameService gameService;

	@GetMapping("/secret-number")
	public ResponseEntity<String> getSecretNumber(@RequestParam Long gameId) {
		String secretNumber = toString(gameService.findGameById(gameId).getSecretNumber());
		return ResponseEntity.ok(secretNumber);
	}

	private String toString(List<Character> characters) {
		return characters.stream()
				.map(String::valueOf)
				.collect(Collectors.joining());
	}
}
