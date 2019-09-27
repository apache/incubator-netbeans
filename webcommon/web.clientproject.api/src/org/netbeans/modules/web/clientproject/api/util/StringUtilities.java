/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.web.clientproject.api.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.openide.util.Parameters;

/**
 * Miscellaneous utility methods for strings.
 * @since 1.20
 */
public final class StringUtilities {

    private StringUtilities() {
    }

    /**
     * Return {@code true} if the String is not {@code null}
     * and has any character after trimming.
     * @param input input <tt>String</tt>, can be {@code null}.
     * @return {@code true} if the String is not {@code null}
     *         and has any character after trimming.
     * @see #isEmpty(String)
     */
    public static boolean hasText(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Return {@code true} if the String is {@code null}
     * or has no characters.
     * @param input input <tt>String</tt>, can be {@code null}
     * @return {@code true} if the String is {@code null}
     *         or has no characters
     * @see  #hasText(String)
     */
    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }

    /**
     * Implode list of strings to one string using delimiter.
     * @param items list of strings to be imploded, can be empty (but not {@code null})
     * @param delimiter delimiter to be used
     * @return one string of imploded strings using delimiter, never {@code null}
     * @see #explode(String, String)
     */
    public static String implode(List<String> items, String delimiter) {
        Parameters.notNull("items", items);
        Parameters.notNull("delimiter", delimiter);

        if (items.isEmpty()) {
            return ""; // NOI18N
        }

        StringBuilder buffer = new StringBuilder(200);
        boolean first = true;
        for (String s : items) {
            if (!first) {
                buffer.append(delimiter);
            }
            buffer.append(s);
            first = false;
        }
        return buffer.toString();
    }

    /**
     * Explode the string using the delimiter.
     * @param string string to be exploded, can be {@code null}
     * @param delimiter delimiter to be used
     * @return list of exploded strings using delimiter
     * @see #implode(List, String)
     */
    public static List<String> explode(String string, String delimiter) {
        Parameters.notNull("delimiter", delimiter); // NOI18N

        if (!hasText(string)) {
            return Collections.<String>emptyList();
        }
        return Arrays.asList(string.split(Pattern.quote(delimiter)));
    }

}
