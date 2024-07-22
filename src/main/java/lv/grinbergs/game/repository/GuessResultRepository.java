package lv.grinbergs.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.grinbergs.game.model.GuessResult;

public interface GuessResultRepository extends JpaRepository<GuessResult, Long> {
}
