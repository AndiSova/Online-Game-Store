package com.ssn.aso.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssn.aso.archieve.GameRepository;
import com.ssn.aso.common.Game;

@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController {
	@Autowired
	GameRepository gameRepository;

	@GetMapping("")
	public Iterable<Game> getAllGames() {
		List<Game> games = new ArrayList<>();
		Iterator<Game> iterator = gameRepository.findAll().iterator();
		iterator.forEachRemaining(game -> {
			game.setImage(getApiUrl() + game.getImage());
			games.add(game);
		});
		Collections.reverse(games);
		return games;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Game> findOne(@PathVariable(value = "id") long id) {
		Optional<Game> gameOptional = gameRepository.findById(id);
		if (!gameOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Game game = gameOptional.get();
		game.setImage(getApiUrl() + game.getImage());
		return ResponseEntity.ok().body(game);
	}

	@PostMapping("")
	public ResponseEntity<Game> create(@Valid @RequestBody Game game) {
		game.setImage(game.getImage().replace(getApiUrl(), ""));
		Game newGame = gameRepository.save(game);
		return ResponseEntity.ok(newGame);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Game> update(@PathVariable(value = "id") Long id, @Valid @RequestBody Game game) {
		Optional<Game> gameOptional = gameRepository.findById(id);
		if (!gameOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Game oldGame = gameOptional.get();
		oldGame.setGameName(game.getGameName());
		oldGame.setPrice(game.getPrice());
		oldGame.setImage(game.getImage().replace(getApiUrl(), ""));

		Game updGame = gameRepository.save(oldGame);
		return ResponseEntity.ok(updGame);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Game> delete(@PathVariable(value = "id") long id) {
		Optional<Game> gameOptional = gameRepository.findById(id);
		Game game = gameOptional.get();
		if (game == null) {
			return ResponseEntity.notFound().build();
		}

		gameRepository.delete(game);
		return ResponseEntity.ok().build();
	}
}