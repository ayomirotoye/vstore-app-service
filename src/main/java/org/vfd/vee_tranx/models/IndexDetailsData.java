package org.vfd.vee_tranx.models;

import java.time.LocalDateTime;
import java.util.HashMap;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexDetailsData {
	private String id;
	String reference;
	@NotNull
	private String tag;
	private HashMap<String, String> data;
	LocalDateTime dateCreated;

}
