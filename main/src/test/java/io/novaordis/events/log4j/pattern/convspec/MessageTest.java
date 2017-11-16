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

import org.junit.Test;

import io.novaordis.events.log4j.pattern.AddResult;
import io.novaordis.events.log4j.pattern.FormatModifier;
import io.novaordis.events.log4j.pattern.Log4jPatternLayoutException;
import io.novaordis.events.log4j.pattern.RenderedLogEvent;
import io.novaordis.events.log4j.pattern.convspec.wildfly.WildFlyMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 10/30/17
 */
public class MessageTest extends ConversionSpecifierTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Overrides -------------------------------------------------------------------------------------------------------

    @Test
    @Override
    public void addAfterLast() throws Exception {

        //
        // noop
        //
    }

    @Test
    @Override
    public void addAfterNotAccepted() throws Exception {

        Message s = getConversionSpecifierToTest(null);

        AddResult r = s.add(' ');
        assertEquals(AddResult.NOT_ACCEPTED, r);

        try {

            s.add(' ');

            fail("should have thrown exception");
        }
        catch(Log4jPatternLayoutException ex) {

            String msg = ex.getMessage();
            assertTrue(msg.contains("attempt to add more characters to a closed conversion pattern component"));
        }
    }

    // Tests -----------------------------------------------------------------------------------------------------------

    // constructors ----------------------------------------------------------------------------------------------------

    @Test
    public void constructor() throws Exception {

        Message s = new Message("m");

        assertEquals(Message.CONVERSION_CHARACTER, s.getConversionCharacter().charValue());
        assertNull(s.getFormatModifier());
        assertEquals("%m", s.getLiteral());
    }

    @Test
    public void constructor_WithFormatModifier() throws Exception {

        Message s = new Message("-5m");

        assertEquals(Message.CONVERSION_CHARACTER, s.getConversionCharacter().charValue());
        FormatModifier m = s.getFormatModifier();
        assertEquals("-5", m.getLiteral());
        assertEquals("%-5m", s.getLiteral());
    }

    @Test
    public void constructor_WrongIdentifier() throws Exception {

        try {

            new Message("c");

            fail("should have thrown exception");
        }
        catch(Log4jPatternLayoutException e) {

            String msg = e.getMessage();

            assertTrue(msg.contains("expected identifier '" + Message.CONVERSION_CHARACTER + "' not found"));
        }
    }

    // getLiteral() ----------------------------------------------------------------------------------------------------

    @Test
    public void getLiteral_TypeSpecific() throws Exception {

        Message s = getConversionSpecifierToTest(null);

        assertEquals("%m", s.getLiteral());
    }

    @Test
    public void getLiteral_TypeSpecific_FormatModifier() throws Exception {

        FormatModifier m = new FormatModifier("-5");
        Message s = getConversionSpecifierToTest(m);

        assertEquals("%-5m", s.getLiteral());
    }

    // parseLogContent() -----------------------------------------------------------------------------------------------

    @Test
    public void parseLogContent_TypeSpecific() throws Exception {

        String line = "this is some message";

        int from = 0;

        WildFlyMessage pe = new WildFlyMessage();

        RenderedLogEvent p = pe.parseLogContent(line, from, null);

        String s = (String)p.get();

        assertEquals(0, p.from());
        assertEquals(20, p.to());
        assertEquals("this is some message", p.getLiteral());
        assertEquals("this is some message", s);
    }

    @Test
    public void parseLogContent_PatternMismatch() throws Exception {

        //
        // we cannot really mismatch a message, as anything is valid as a message - noop
        //
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    @Override
    protected String getMatchingLogContent() throws Exception {

        return "this is a synthetic message";
    }

    @Override
    protected Message getConversionSpecifierToTest(FormatModifier m) throws Exception {

        Message cs = new Message();
        cs.setFormatModifier(m);
        return cs;
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
