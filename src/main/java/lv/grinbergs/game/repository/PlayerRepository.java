package lv.grinbergs.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.grinbergs.game.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByName(String name);
}
