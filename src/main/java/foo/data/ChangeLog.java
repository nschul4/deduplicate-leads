package foo.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChangeLog {

	@JsonProperty("from")
	Lead from;

	@JsonProperty("to")
	Lead to;

	@JsonProperty("propertyChanges")
	List<String> propertyChanges;
}
