package com.example.microservicioparent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * The type Parent.
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Parents")
@Builder
@AllArgsConstructor
public class Parent {

	@Id
	private String id;

	@Size(min = 3, max = 25)
	@NotEmpty
	private String fullName;
	@NotEmpty
	private String gender;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate birthday;
	@NotEmpty
	private String typeDocument;
	@NotEmpty
	@Size(min = 8, max = 8)
	private String document;


}
