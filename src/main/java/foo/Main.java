package foo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import foo.data.ChangeLog;
import foo.data.Lead;
import foo.data.LeadWrapper;

public class Main {

	public static void main(final String[] args) throws Exception {
		final List<Lead> leads = LeadsFileJson.loadLeadsFromFile("leads.json");
		final LeadProcessor leadProcessor = new LeadProcessor();
		final List<ChangeLog> changeLogs = new ArrayList<>();
		final List<Lead> deduplicatedLeads = leadProcessor.processLeads(leads, changeLogs);
		writeLeadsToFile("deduplicatedLeads.json", new LeadWrapper(deduplicatedLeads));
		saveChangeLog("changeLog.json", changeLogs);
	}

	private static void writeLeadsToFile(final String outputFilename, final LeadWrapper leadWrapper)
			throws IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilename), leadWrapper);
	}

	private static void saveChangeLog(final String logFilename, final List<ChangeLog> changeLogs) throws IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(logFilename), changeLogs);
	}
}
