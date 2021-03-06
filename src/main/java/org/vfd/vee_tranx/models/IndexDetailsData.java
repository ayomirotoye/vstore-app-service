package org.vfd.vee_tranx.models;

import java.time.LocalDateTime;
import java.util.HashMap;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndexDetailsData {
	private String id;
	String reference;
	@NotNull
	private String tag;
	private HashMap<String, Object> data;
	LocalDateTime dateCreated;

}
