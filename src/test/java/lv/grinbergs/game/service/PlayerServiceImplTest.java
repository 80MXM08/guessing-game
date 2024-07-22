package lv.grinbergs.game.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lv.grinbergs.game.model.Player;
import lv.grinbergs.game.repository.PlayerRepository;

class PlayerServiceImplTest {

	@Mock
	private PlayerRepository playerRepository;

	@InjectMocks
	private PlayerServiceImpl playerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getsPlayerWhenPlayerExists() {
		Player player = new Player();
		player.setName("Player");

		when(playerRepository.findByName("Player")).thenReturn(player);

		Player result = playerService.getOrCreate("Player");

		assertEquals("Player", result.getName());
		verify(playerRepository, times(1)).findByName("Player");
		verify(playerRepository, times(0)).save(any(Player.class));
	}

	@Test
	void createsPlayerWhenPlayerDoesNotExist() {
		when(playerRepository.findByName("Player")).thenReturn(null);

		Player result = playerService.getOrCreate("Player");

		assertNotNull(result);
		assertEquals("Player", result.getName());
		assertEquals(0, result.getGamesPlayed());
		assertEquals(0, result.getCorrectGuesses());
		verify(playerRepository, times(1)).findByName("Player");
		verify(playerRepository, times(1)).save(any(Player.class));
	}

	@Test
	void updatesCorrectGuessesAndGamesPlayed() {
		Player player = new Player();
		player.setCorrectGuesses(5);
		player.setGamesPlayed(10);

		playerService.update(player, true);

		assertEquals(6, player.getCorrectGuesses());
		assertEquals(11, player.getGamesPlayed());
		verify(playerRepository, times(1)).save(player);
	}

	@Test
	void updatesGamesPlayed() {
		Player player = new Player();
		player.setCorrectGuesses(5);
		player.setGamesPlayed(10);

		playerService.update(player, false);

		assertEquals(5, player.getCorrectGuesses());
		assertEquals(11, player.getGamesPlayed());
		verify(playerRepository, times(1)).save(player);
	}

	@Test
	void findsTop() {
		Player player1 = new Player();
		player1.setName("Player");
		player1.setGamesPlayed(5);
		player1.setCorrectGuesses(3);

		Player player2 = new Player();
		player2.setName("Jane");
		player2.setGamesPlayed(15);
		player2.setCorrectGuesses(10);

		Player player3 = new Player();
		player3.setName("Doe");
		player3.setGamesPlayed(10);
		player3.setCorrectGuesses(3);

		when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2, player3));

		List<Player> result = playerService.findTop(6);

		assertEquals(2, result.size());
		assertThat(result).containsExactlyInAnyOrder(player2, player3);
	}
}
