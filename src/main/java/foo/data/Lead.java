package foo.data;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class Lead {

	@JsonProperty("_id")
	private String id;

	@JsonProperty("email")
	private String email;

	@JsonSerialize(using = CustomSerializerZonedDateTime.class)
	@JsonDeserialize(using = CustomDeserializerZonedDateTime.class)
	private ZonedDateTime entryDate;

	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonAnySetter
	public void setAdditionalProperty(final String key, final Object value) {
		this.additionalProperties.put(key, value);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public static Lead copy(final Lead lead) {
		final Lead newLead = new Lead();
		newLead.setId(lead.getId());
		newLead.setEmail(lead.getEmail());
		newLead.setEntryDate(lead.getEntryDate());
		for (final Entry<String, Object> entry : lead.getAdditionalProperties().entrySet()) {
			newLead.getAdditionalProperties().put(entry.getKey(), entry.getValue());
		}
		return newLead;
	}
}
