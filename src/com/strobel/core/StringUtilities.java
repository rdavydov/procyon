package com.strobel.core;

import com.strobel.util.ContractUtils;

/**
 * @author Mike Strobel
 */
public final class StringUtilities {
    public final static String EMPTY = "";

    private StringUtilities() {
        throw ContractUtils.unreachable();
    }

    private final static StringComparator[] _comparators = new StringComparator[]{StringComparator.Ordinal, StringComparator.OrdinalIgnoreCase};

    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.length() == 0;
    }

    public static boolean equals(final String s1, final String s2) {
        return StringComparator.Ordinal.equals(s1, s2);
    }

    public static boolean equals(final String s1, final String s2, final StringComparison comparison) {
        return _comparators[VerifyArgument.notNull(comparison, "comparison").ordinal()].equals(s1, s2);
    }

    public static int compare(final String s1, final String s2) {
        return StringComparator.Ordinal.compare(s1, s2);
    }

    public static int compare(final String s1, final String s2, final StringComparison comparison) {
        return _comparators[VerifyArgument.notNull(comparison, "comparison").ordinal()].compare(s1, s2);
    }

    public static int getHashCode(final String s) {
        if (isNullOrEmpty(s)) {
            return 0;
        }
        return s.hashCode();
    }

    public static int getHashCodeIgnoreCase(final String s) {
        if (isNullOrEmpty(s)) {
            return 0;
        }

        int hash = 0;

        for (int i = 0, n = s.length(); i < n; i++) {
            hash = 31 * hash + Character.toLowerCase(s.charAt(i));
        }

        return hash;
    }

    public static boolean isNullOrWhitespace(final String s) {
        if (isNullOrEmpty(s)) {
            return true;
        }
        for (int i = 0, length = s.length(); i < length; i++) {
            final char ch = s.charAt(i);
            if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }

    public static boolean startsWithIgnoreCase(final String s, final String prefix) {
        return startsWithIgnoreCase(s, prefix, 0);
    }

    public static boolean startsWithIgnoreCase(final String s, final String prefix, final int offset) {
        final int sourceLength = VerifyArgument.notNull(s, "s").length();
        final int prefixLength = VerifyArgument.notNull(prefix, "prefix").length();

        final int n = sourceLength - offset;

        if (prefixLength > n) {
            return false;
        }

        for (int i = 0; i < prefixLength; i++) {
            final char c1 = s.charAt(offset + i);
            final char c2 = prefix.charAt(i);
            if (c1 != c2) {
                if (Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static String concat(final Iterable<String> values) {
        return join(null, values);
    }

    public static String concat(final String... values) {
        return join(null, values);
    }

    public static String join(final String separator, final Iterable<String> values) {
        VerifyArgument.notNull(values, "values");

        final StringBuilder sb = new StringBuilder();

        boolean appendSeparator = false;

        for (final String value : values) {
            if (value == null) {
                continue;
            }

            if (appendSeparator) {
                sb.append(separator);
            }

            appendSeparator = true;

            sb.append(value);
        }

        return sb.toString();
    }

    public static String join(final String separator, final String... values) {
        if (ArrayUtilities.isNullOrEmpty(values)) {
            return EMPTY;
        }

        final StringBuilder sb = new StringBuilder();

        for (int i = 0, n = values.length; i < n; i++) {
            final String value = values[i];

            if (value == null) {
                continue;
            }

            if (i != 0 && separator != null) {
                sb.append(separator);
            }

            sb.append(value);
        }

        return sb.toString();
    }

    public static boolean substringEquals(
        final String value,
        final int offset,
        final String comparand,
        final int comparandOffset,
        final int substringLength) {

        return substringEquals(
            value,
            offset,
            comparand,
            comparandOffset,
            substringLength,
            StringComparison.Ordinal
        );
    }

    public static boolean substringEquals(
        final String value,
        final int offset,
        final String comparand,
        final int comparandOffset,
        final int substringLength,
        final StringComparison comparison) {

        VerifyArgument.notNull(value, "value");
        VerifyArgument.notNull(comparand, "comparand");

        VerifyArgument.isNonNegative(offset, "offset");
        VerifyArgument.isNonNegative(comparandOffset, "comparandOffset");

        VerifyArgument.isNonNegative(substringLength, "substringLength");

        final int valueLength = value.length();

        if (offset + substringLength > valueLength) {
            return false;
        }

        final int comparandLength = comparand.length();

        if (comparandOffset + substringLength > comparandLength) {
            return false;
        }

        final boolean ignoreCase = comparison == StringComparison.OrdinalIgnoreCase;

        for (int i = 0; i < substringLength; i++) {
            final char vc = value.charAt(offset + i);
            final char cc = comparand.charAt(comparandOffset + i);

            if (vc == cc || ignoreCase && Character.toLowerCase(vc) == Character.toLowerCase(cc)) {
                continue;
            }

            return false;
        }

        return true;
    }
}