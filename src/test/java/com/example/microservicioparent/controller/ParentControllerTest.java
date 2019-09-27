package com.example.microservicioparent.controller;

import com.example.microservicioparent.model.Parent;
import com.example.microservicioparent.repository.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Parent controller test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ParentControllerTest {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ParentRepository parentRepository;
	private WebTestClient client;
	private List<Parent> expectedProducts;

	@BeforeEach
	void setUp() {
		client = WebTestClient
			.bindToApplicationContext(applicationContext)
			.configureClient()
			.baseUrl("")
			.build();

		Flux<Parent> initData = parentRepository.deleteAll()
			.thenMany(Flux.just(
				Parent.builder().id("12345").fullName("omar").gender("male").birthday(LocalDate.of(1993, 2, 3)).typeDocument("dni").document("73674232").build(),
				Parent.builder().id("s1234").fullName("jose").gender("male").birthday(LocalDate.of(1993, 2, 3)).typeDocument("dni").document("76786787").build())
				.flatMap(parentRepository::save))
			.thenMany(parentRepository.findAll());

		expectedProducts = initData.collectList().block();


	}


	@Test
	void create() {

		Parent expectedProduct = expectedProducts.get(0);

		client.post().uri("/").body(Mono.just(expectedProduct), Parent.class).exchange()
			.expectStatus().isCreated();

	}

	@Test
	void findAll() {

		client.get().uri("/").exchange()
			.expectStatus().isOk();
	}

	@Test
	void findById() {

		String title = "s1234";
		client.get().uri("/{title}", title).exchange()
			.expectStatus().isOk();
	}

	@Test
	void update() {

		Parent expectedProduct = expectedProducts.get(0);

		client.put().uri("/{id}", expectedProduct.getId()).body(Mono.just(expectedProduct), Parent.class).exchange()
			.expectStatus().isOk();

	}

	@Test
	void eliminar() {
		Parent productToDelete = expectedProducts.get(0);
		client.delete().uri("/{id}", productToDelete.getId()).exchange()
			.expectStatus().isNoContent();
	}

	@Test
	void findByDocument() {

		String title = "76786787";
		client.get().uri("/document/{title}", title).exchange()
			.expectStatus().isOk();
	}

	@Test
	void findByFullName() {


		String title = "jose";
		client.get().uri("/name/{title}", title).exchange()
			.expectStatus().isOk();
	}

	@Test
	void findBybirthdayBetween() {

		LocalDate date1 = LocalDate.of(1800, 03, 02);
		LocalDate date2 = LocalDate.of(2000, 03, 02);


		client.get().uri("/date/{date1}/{date2}", date1, date2).exchange()
			.expectStatus().isOk().
			expectBodyList(Parent.class);

	}
}