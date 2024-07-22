package lv.grinbergs.game.business.rng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SecretNumberGenerator {
	public static List<Character> generate(int length) {
		List<Character> list = new ArrayList<>();
		for (int i=0; i<=9; i++) list.add(String.valueOf(i).charAt(0));
		Collections.shuffle(list);
		return list.subList(0, length);
	}
}
