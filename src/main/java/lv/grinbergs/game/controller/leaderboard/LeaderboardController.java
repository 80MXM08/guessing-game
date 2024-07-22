package lv.grinbergs.game.controller.leaderboard;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.grinbergs.game.model.Player;
import lv.grinbergs.game.service.PlayerService;

@Controller
public class LeaderboardController {

	@Autowired
	private PlayerService playerService;

	@RequestMapping("/leaderboard")
	public String leaderboard(@RequestParam Optional<Integer> minGames, Model model) {
		minGames.ifPresent(min -> {
			List<Player> players = playerService.findTop(minGames.get());
			model.addAttribute("players", players);
		});
		return "leaderboard";
	}
}
