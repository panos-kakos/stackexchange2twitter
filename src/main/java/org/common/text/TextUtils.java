package org.common.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public final class TextUtils {

    private TextUtils() {
        throw new AssertionError();
    }

    // API

    public static List<String> extractUrls(final String value) {
        Preconditions.checkNotNull(value);
        final List<String> result = new ArrayList<String>();

        final String urlPattern = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        final Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        final Matcher m = p.matcher(value);
        while (m.find()) {
            result.add(value.substring(m.start(0), m.end(0)));
        }
        return result;
    }

    public static List<String> extractUrlsAlternative(final String input) {
        final List<String> result = new ArrayList<String>();

        final Pattern pattern = Pattern.compile("\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + "|mil|biz|info|mobi|name|aero|jobs|museum" + "|travel|[a-z]{2}))(:[\\d]{1,5})?"
                + "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*"
                + "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        final Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public static String determineMainUrl(final List<String> extractedUrls) {
        for (final String urlCandidate : extractedUrls) {
            if (urlCandidate.contains("plus.google.com") || urlCandidate.endsWith(".git")) {
                continue;
            }

            return urlCandidate;
        }

        return null;
    }

}