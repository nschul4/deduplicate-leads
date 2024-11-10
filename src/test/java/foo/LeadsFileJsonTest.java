package foo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import foo.data.Lead;

class LeadsFileJsonTest {

	@Test
	void testLoadLeadsFromFile() throws IOException {
		final List<Lead> leads = LeadsFileJson.loadLeadsFromFile("leads.json");
		assertEquals(10, leads.size());
	}
}
