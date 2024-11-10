package foo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import foo.data.Lead;

class LeadProcessorTest {
	final LeadProcessor leadProcessor = new LeadProcessor();

	@Test
	void test1LeadUnset() {
		final List<Lead> leads = new ArrayList<>();

		final Lead lead = new Lead();
		leads.add(lead);

		final Collection<Lead> deduplicatedLeads = leadProcessor.processLeads(leads);
		assertEquals(1, deduplicatedLeads.size());
	}

	@Test
	void test2LeadsCollidingNone() {
		final List<Lead> leads = new ArrayList<>();

		final Lead lead1 = new Lead();
		lead1.setId("123");
		lead1.setEmail("qwe@asd.zxc");
		leads.add(lead1);

		final Lead lead2 = new Lead();
		lead2.setId("234");
		lead2.setEmail("asd@zxc.qwe");
		leads.add(lead2);

		final Collection<Lead> dedupedLeads = leadProcessor.processLeads(leads);
		assertEquals(2, dedupedLeads.size());
	}

	@Test
	void test2LeadsCollidingById() {
		final String collidingId = "123";

		final List<Lead> leads = new ArrayList<>();

		final Lead lead1 = new Lead();
		lead1.setId(collidingId);
		leads.add(lead1);

		final Lead lead2 = new Lead();
		lead2.setId(collidingId);
		leads.add(lead2);

		final Collection<Lead> dedupedLeads = leadProcessor.processLeads(leads);
		assertEquals(1, dedupedLeads.size());
	}

	@Test
	void test2LeadsCollidingByEmail() {
		final String collidingEmail = "qwe@asd.zxc";

		final List<Lead> leads = new ArrayList<>();

		final Lead lead1 = new Lead();
		lead1.setId("123");
		lead1.setEmail(collidingEmail);
		leads.add(lead1);

		final Lead lead2 = new Lead();
		lead1.setId("234");
		lead2.setEmail(collidingEmail);
		leads.add(lead2);

		final Collection<Lead> dedupedLeads = leadProcessor.processLeads(leads);
		assertEquals(1, dedupedLeads.size());
	}

	@Test
	void test2LeadsCollidingByIdWithAdditionalProperties() {
		final ZonedDateTime zoneDateTime1 = ZonedDateTime.now();
		final ZonedDateTime zoneDateTime2 = zoneDateTime1.minusMonths(1);
		assertEquals(true, zoneDateTime1.compareTo(zoneDateTime2) > 0);

		final String collidingId = "123";

		final List<Lead> leads = new ArrayList<>();

		final String additionalProperty1 = "firstName";
		final String additionalProperty2 = "lastName";

		final Lead lead1 = new Lead();
		lead1.setId(collidingId);
		lead1.setEmail("qwe@asd.zxc");
		lead1.setEntryDate(zoneDateTime1);
		lead1.getAdditionalProperties().put(additionalProperty1, "Ted");
		lead1.getAdditionalProperties().put(additionalProperty2, "Wallace");
		leads.add(lead1);

		final Lead lead2 = new Lead();
		lead2.setId(collidingId);
		lead2.setEmail("asd@zxc.qwe");
		lead2.setEntryDate(zoneDateTime2);
		lead2.getAdditionalProperties().put(additionalProperty1, "Fran");
		leads.add(lead2);

		final Collection<Lead> dedupedLeads = leadProcessor.processLeads(leads);
		assertEquals(1, dedupedLeads.size());

		final Lead mergedLead = dedupedLeads.iterator().next();
		assertEquals(lead2.getId(), mergedLead.getId());
		assertEquals(lead2.getEmail(), mergedLead.getEmail());
		assertEquals(true, lead2.getEntryDate().isEqual(mergedLead.getEntryDate()));
		assertNotNull(mergedLead.getAdditionalProperties().get(additionalProperty1));
		assertNotNull(mergedLead.getAdditionalProperties().get(additionalProperty2));
		assertEquals(lead2.getAdditionalProperties().get(additionalProperty1),
				mergedLead.getAdditionalProperties().get(additionalProperty1));
		assertEquals(lead1.getAdditionalProperties().get(additionalProperty2),
				mergedLead.getAdditionalProperties().get(additionalProperty2));
	}

	@Test
	void test2LeadsCollidingByIdWithAdditionalProperties2() {
		final ZonedDateTime zoneDateTime2 = ZonedDateTime.now();
		final ZonedDateTime zoneDateTime1 = zoneDateTime2.minusMonths(1);
		assertEquals(true, zoneDateTime2.compareTo(zoneDateTime1) > 0);

		final String collidingId = "123";

		final List<Lead> leads = new ArrayList<>();

		final String additionalProperty1 = "firstName";
		final String additionalProperty2 = "lastName";

		final Lead lead1 = new Lead();
		lead1.setId(collidingId);
		lead1.setEntryDate(zoneDateTime1);
		lead1.getAdditionalProperties().put(additionalProperty1, "Ted");
		lead1.getAdditionalProperties().put(additionalProperty2, "Wallace");
		leads.add(lead1);

		final Lead lead2 = new Lead();
		lead2.setId(collidingId);
		lead2.setEntryDate(zoneDateTime2);
		lead2.getAdditionalProperties().put(additionalProperty1, "Fran");
		leads.add(lead2);

		final Collection<Lead> dedupedLeads = leadProcessor.processLeads(leads);
		assertEquals(1, dedupedLeads.size());

		final Lead mergedLead = dedupedLeads.iterator().next();
		assertEquals(lead2.getId(), mergedLead.getId());
		assertEquals(lead2.getEmail(), mergedLead.getEmail());
		assertEquals(true, lead1.getEntryDate().isEqual(mergedLead.getEntryDate()));
		assertNotNull(mergedLead.getAdditionalProperties().get(additionalProperty1));
		assertNotNull(mergedLead.getAdditionalProperties().get(additionalProperty2));
		assertEquals(lead1.getAdditionalProperties().get(additionalProperty1),
				mergedLead.getAdditionalProperties().get(additionalProperty1));
		assertEquals(lead1.getAdditionalProperties().get(additionalProperty2),
				mergedLead.getAdditionalProperties().get(additionalProperty2));
	}
}
