package foo.data;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

class CustomDeserializerZonedDateTime extends JsonDeserializer<ZonedDateTime> {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

	@Override
	public ZonedDateTime deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
		return ZonedDateTime.parse(p.getText(), FORMATTER);
	}
}
