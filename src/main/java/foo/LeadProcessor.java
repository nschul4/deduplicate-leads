package foo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import foo.data.ChangeLog;
import foo.data.Lead;

public class LeadProcessor {

	private final Map<String, Lead> uniqueLeadsById = new HashMap<>();
	private final Map<String, Lead> uniqueLeadsByEmail = new HashMap<>();
	private final ArrayList<Lead> dediplicatedLeads = new ArrayList<>();

	public List<Lead> processLeads(final List<Lead> leads) {
		return processLeads(leads, new ArrayList<>());
	}

	public List<Lead> processLeads(final List<Lead> leads, final List<ChangeLog> changeLogs) {
		for (final Lead lead : leads) {
			final Lead existingLead = getExistingLead(lead);
			if (existingLead == null) {
				dediplicatedLeads.add(lead);
				putLead(lead);
			} else {
				removeLead(existingLead);
				final Lead mergedLead = mergeLeads(existingLead, lead, changeLogs);
				putLead(mergedLead);
			}
		}
		return dediplicatedLeads;
	}

	private Lead getExistingLead(final Lead newLead) {
		final Lead existingLeadById = uniqueLeadsById.get(newLead.getId());
		if (existingLeadById != null) {
			return existingLeadById;
		}
		final Lead existingLeadByEmail = uniqueLeadsByEmail.get(newLead.getEmail());
		if (existingLeadByEmail != null) {
			return existingLeadByEmail;
		}
		return null;
	}

	private void putLead(final Lead lead) {
		uniqueLeadsById.put(lead.getId(), lead);
		uniqueLeadsByEmail.put(lead.getEmail(), lead);
	}

	private void removeLead(final Lead lead) {
		uniqueLeadsById.remove(lead.getId());
		uniqueLeadsByEmail.remove(lead.getEmail());
	}

	private Lead mergeLeads(final Lead existingLead, final Lead newLead, final List<ChangeLog> changeLogs) {
		if (existingLead.getEntryDate() == null) {
			LeadMergeUtil.leadMergeConditional(newLead, existingLead, changeLogs);
		} else if (newLead.getEntryDate() == null) {
			LeadMergeUtil.leadMergeUnConditional(newLead, existingLead, changeLogs);
		} else {
			final int dateComparison = newLead.getEntryDate().compareTo(existingLead.getEntryDate());
			if (dateComparison < 0) {
				// newLead's entryDate is before existingLead's
				LeadMergeUtil.leadMergeUnConditional(newLead, existingLead, changeLogs);
			} else if (dateComparison > 0) {
				// newLead's entry date is after existingLead's
				LeadMergeUtil.leadMergeConditional(newLead, existingLead, changeLogs);
			} else {
				// entry date is the same
				LeadMergeUtil.leadMergeUnConditional(newLead, existingLead, changeLogs);
			}
		}
		return existingLead;
	}
}
