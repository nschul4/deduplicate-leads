package foo.data;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

class CustomSerializerZonedDateTime extends JsonSerializer<ZonedDateTime> {
	private static final Logger logger = LoggerFactory.getLogger(CustomSerializerZonedDateTime.class);
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

	@Override
	public void serialize(final ZonedDateTime dateTime, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
		final String formattedDate = dateTime.format(FORMATTER);
		if (formattedDate.endsWith("Z")) {
			final String fixedFormattedDate = formattedDate.replace("Z", "+00:00");
			logger.info("___serialize_mod_: {}", fixedFormattedDate);
			gen.writeString(fixedFormattedDate);
		} else {
			logger.info("___serialize_____: {}", formattedDate);
			gen.writeString(formattedDate);
		}
	}
}
