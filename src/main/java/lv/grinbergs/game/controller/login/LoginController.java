package lv.grinbergs.game.controller.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.grinbergs.game.controller.game.GuessForm;
import lv.grinbergs.game.model.Game;
import lv.grinbergs.game.model.Player;
import lv.grinbergs.game.service.GameService;
import lv.grinbergs.game.service.PlayerService;

@Controller
public class LoginController {

	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;

	@RequestMapping("/login")
	public String start(@AuthenticationPrincipal OAuth2User principal, @RequestParam Optional<String> name, Model model) {
		String actualName = name.or(() -> getSocialName(principal)).orElseThrow();
		Player player = playerService.getOrCreate(actualName);
		Game game = gameService.startNewGame(player);
		model.addAttribute("game", game);
		model.addAttribute("guessForm", new GuessForm());
		return "game";
	}

	private Optional<String> getSocialName(OAuth2User principal) {
		return Optional.ofNullable(principal)
				.map(oAuth2User -> oAuth2User.getAttribute("name"));
	}
}
