package libranet;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DurationParser {
    private static final Pattern PATTERN =
            Pattern.compile("^(\\d+)\\s*(d|day|days|w|wk|week|weeks|m|mo|month|months)?$", Pattern.CASE_INSENSITIVE);

    // Use this method name (call parseDays(...) from Library/main)
    public static int parseDays(String raw) {
        if (raw == null) throw new IllegalArgumentException("Duration string is null");
        String s = raw.trim();
        Matcher m = PATTERN.matcher(s);
        if (!m.matches()) throw new IllegalArgumentException("Cannot parse duration: '" + raw +
                "'. Examples: '7', '7d', '2w', '1 month'");
        long value = Long.parseLong(m.group(1));
        String unit = m.group(2);
        if (unit == null) return (int) value;
        unit = unit.toLowerCase(Locale.ROOT);
        if (unit.startsWith("d")) return (int) value;
        if (unit.startsWith("w")) return (int) (value * 7);
        if (unit.startsWith("m")) return (int) (value * 30); // approximate
        return (int) value;
    }
}
