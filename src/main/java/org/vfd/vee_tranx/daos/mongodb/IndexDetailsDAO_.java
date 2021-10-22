package org.vfd.vee_tranx.daos.mongodb;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("indexes")
public class IndexDetailsDAO_ {
	@Id
	private String id;
	@Indexed(unique = true)
	String reference;
	@NotNull
	String tag;
	@TextIndexed
	Collection<String> fieldValues;
	String rawData;
	LocalDateTime dateCreated;
	@TextScore
	private Float score;
}
