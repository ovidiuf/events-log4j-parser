/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.events.log4j.pattern.convspec;

import io.novaordis.events.log4j.impl.Log4jEventImpl;
import io.novaordis.events.log4j.pattern.ConversionPatternComponent;
import io.novaordis.events.log4j.pattern.Log4jPatternLayoutException;
import io.novaordis.events.log4j.pattern.ProcessedString;
import io.novaordis.events.log4j.pattern.RenderedLogEvent;

/**
 * The name of the thread that generated the logging event.
 *
 * 't'
 *
 * The type of the corresponding parsed object instance is a String containing the thread name.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 10/30/17
 */
public class ThreadName extends ConversionSpecifierBase {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final char CONVERSION_CHARACTER = 't';

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // ConversionSpecifier implementation ------------------------------------------------------------------------------

    @Override
    public Character getConversionCharacter() {

        return CONVERSION_CHARACTER;
    }

    @Override
    protected RenderedLogEvent parseLiteralAfterFormatModifierWasUnapplied(ProcessedString ps)
            throws Log4jPatternLayoutException {

        return new RenderedLogEvent(ps.getProcessedString(), ps.from(), ps.to());
    }

    @Override
    public Integer findNext(String logContent, int from) {

        ConversionPatternComponent.checkConsistency(logContent, from);

        if (logContent.length() == from) {

            return null;
        }

        throw new RuntimeException("findNext() NOT YET IMPLEMENTED");
    }

    @Override
    public void injectIntoEvent(Log4jEventImpl e, Object value) {

        if (value == null) {

            //
            // noop
            //

            return;
        }

        if (!(value instanceof String)) {

            throw new IllegalArgumentException(
                    "invalid value type " + value.getClass().getSimpleName() + ", expected String");
        }

        e.setThreadName((String)value);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
