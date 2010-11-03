package com.gwtplatform.samples.basic.server;

import org.apache.commons.lang3.StringUtils;

public class Parser {

	private static final String THIS_CHARACTER_IS_NOT_ALLOWED = "This character is not allowed";
	public static final String SACRED_STRING = "~";

	public static String parse(String input) throws InvalidCharacterExpection {
		String output = null;
		if (StringUtils.isNotBlank(input)) {
			if (StringUtils.contains(input, SACRED_STRING)) {
				// TODO This need to be worked on . Better exception detail . Do
				// we need this in the first place . We can also replace it with
				// something else on detection
				throw new InvalidCharacterExpection(
						THIS_CHARACTER_IS_NOT_ALLOWED);
			}
			for (int i = 0; i < input.length(); i++) {
				if (i == 0) {
					output = StringUtils.mid(input, i, input.length()
							- (input.length() - (i + 1)));
				} else {
					output = output + SACRED_STRING
							+ StringUtils.mid(input, i, 1);
					// 7- (7-0)
				}
			}
		}
		return output;
	}

}
