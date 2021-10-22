package org.vfd.vee_tranx.enums;

import lombok.Getter;
import lombok.Setter;

public enum ResponseCodeEnum {
	OK("Successful"), BAD_REQUEST("Someting is wrong with your requst / Something happened"),
	NO_RECORD("No reecord found");

	@Getter
	@Setter
	private String message;

	ResponseCodeEnum(String message) {
		this.message = message;
	}
}
