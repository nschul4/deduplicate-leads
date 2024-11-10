package foo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import foo.data.Lead;

public class LeadsFileJson {

	private LeadsFileJson() {
	}

	public static List<Lead> loadLeadsFromFile(String intputFilename) throws IOException {
		final File jsonFile = new File(intputFilename);
		final ObjectMapper objectMapper = new ObjectMapper();

		final List<Lead> leadsList = new ArrayList<>();

		// Read JSON file and parse it into a JsonNode
		final JsonNode rootNode = objectMapper.readTree(jsonFile);

		// Access the "leads" array
		final JsonNode leadsArray = rootNode.path("leads");

		// Loop through each item in the "leads" array
		for (final JsonNode lead : leadsArray) {
			// Convert each lead JsonNode to a Map
			final Lead leadMap = objectMapper.convertValue(lead, Lead.class);

			// Add the map to the list
			leadsList.add(leadMap);
		}

		return leadsList;
	}
}
