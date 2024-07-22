package lv.grinbergs.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.grinbergs.game.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
