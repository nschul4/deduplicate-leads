package foo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import foo.data.ChangeLog;
import foo.data.Lead;

public class LeadMergeUtil {

	private LeadMergeUtil() {
	}

	public static void leadMergeConditional(final Lead from, final Lead to, final List<ChangeLog> changeLogs) {
		final ChangeLog changeLog = createChangeLog(from, to);
		changeLogs.add(changeLog);
		if (to.getId() == null && from.getId() != null) {
			final String strFrom = from.getId();
			final String strTo = to.getId();
			addToChangeLog(changeLog, "id", strFrom, strTo);
			to.setId(from.getId());
		}
		if (to.getEmail() == null && from.getEmail() != null) {
			final String strFrom = from.getEmail();
			final String strTo = to.getEmail();
			addToChangeLog(changeLog, "email", strFrom, strTo);
			to.setEmail(from.getEmail());
		}
		if (to.getEntryDate() == null && from.getEntryDate() != null) {
			final String strFrom = String.valueOf(from.getEntryDate());
			final String strTo = String.valueOf(to.getEntryDate());
			addToChangeLog(changeLog, "entryDate", strFrom, strTo);
			to.setEntryDate(from.getEntryDate());
		}
		for (final Entry<String, Object> entry : from.getAdditionalProperties().entrySet()) {
			if (to.getAdditionalProperties().get(entry.getKey()) == null && entry.getValue() != null) {
				final String strFrom = String.valueOf(entry.getValue());
				final String strTo = String.valueOf(to.getAdditionalProperties().get(entry.getKey()));
				addToChangeLog(changeLog, entry.getKey(), strFrom, strTo);
				to.getAdditionalProperties().put(entry.getKey(), entry.getValue());
			}
		}
	}

	public static void leadMergeUnConditional(final Lead from, final Lead to, final List<ChangeLog> changeLogs) {
		final ChangeLog changeLog = createChangeLog(from, to);
		changeLogs.add(changeLog);
		//
		if (!Objects.equals(from.getId(), to.getId())) {
			final String strFrom = from.getId();
			final String strTo = to.getId();
			addToChangeLog(changeLog, "id", strFrom, strTo);
			to.setId(from.getId());
		}
		//
		//
		if (!Objects.equals(from.getEmail(), to.getEmail())) {
			final String strFrom = from.getEmail();
			final String strTo = to.getEmail();
			addToChangeLog(changeLog, "email", strFrom, strTo);
			to.setEmail(from.getEmail());
		}
		//
		//
		if (!Objects.equals(from.getEntryDate(), to.getEntryDate())) {
			final String strFrom = String.valueOf(from.getEntryDate());
			final String strTo = String.valueOf(to.getEntryDate());
			addToChangeLog(changeLog, "entryDate", strFrom, strTo);
			to.setEntryDate(from.getEntryDate());
		}
		//
		//
		for (final Entry<String, Object> entry : from.getAdditionalProperties().entrySet()) {
			if (!Objects.equals(entry.getValue(), to.getAdditionalProperties().get(entry.getKey()))) {
				final String strFrom = String.valueOf(entry.getValue());
				final String strTo = String.valueOf(to.getAdditionalProperties().get(entry.getKey()));
				addToChangeLog(changeLog, entry.getKey(), strFrom, strTo);
				to.getAdditionalProperties().put(entry.getKey(), entry.getValue());
			}
		}
	}

	private static ChangeLog createChangeLog(final Lead from, final Lead to) {
		final ChangeLog changeLog = new ChangeLog();
		changeLog.setPropertyChanges(new ArrayList<>());
		changeLog.setFrom(from.deepCopy());
		changeLog.setTo(to.deepCopy());
		return changeLog;
	}

	private static void addToChangeLog(final ChangeLog changeLog, final String propertyName, final String valueFrom,
			final String valueTo) {
		changeLog.getPropertyChanges().add(String.format("[%s]: from:[%s], to:[%s]", propertyName, valueFrom, valueTo));
	}
}
