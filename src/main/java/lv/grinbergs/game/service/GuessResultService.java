package lv.grinbergs.game.service;

import java.util.List;

import lv.grinbergs.game.model.GuessResult;

public interface GuessResultService {
	GuessResult create(List<Character> secretNumber, List<Character> guess);
}
