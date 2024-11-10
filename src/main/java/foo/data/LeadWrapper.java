package foo.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeadWrapper {
	@JsonProperty("leads")
	final List<Lead> leads;

	public LeadWrapper(final List<Lead> leads) {
		this.leads = leads;
	}
}
