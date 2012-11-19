/* 
 * Copyright (C) 2002-2012 Berthold Firmenich
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, look at "http://www.gnu.org/copyleft/gpl.html" or
 * write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * Contact address: berthold.firmenich@cademia.org
 * Homepage       : http://www.cademia.org/
 */

package cib.util.dxf;

import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cib.util.dxf.interpreter.DXFParser;
import cib.util.dxf.interpreter.ParseException;
import cib.util.dxf.interpreter.TokenMgrError;

/** 
 * A DXFParser instance reads a DXF source and notifies the registered Content
 * Handler about occurring events.
 * 
 * @author Berthold Firmenich
 * @version $Revision: 1717 $, $Author: svn-firmenich $
 * @version $Date: 2012-02-29 14:42:17 +0100 (Wed, 29 Feb 2012) $
 */
public class DXFReader {

    // Constants
    public final static String SECTION_HEADER = "HEADER";
    public final static String SECTION_CLASSES = "CLASSES";
    public final static String SECTION_TABLES = "TABLES";
    public final static String SECTION_BLOCKS = "BLOCKS";
    public final static String SECTION_ENTITIES = "ENTITIES";
    public final static String SECTION_OBJECTS = "OBJECTS";
    public final static String SECTION_THUMBNAILIMAGE = "THUMBNAILIMAGE";
    
    // Attributes
    private Set<ContentHandler> m_contentHandlers =
        new HashSet<ContentHandler>();
    private DXFParser m_parser = null;

    /**
     * Allows an application to add a content event handler.
     * 
     * @param handler the Content Handler to be added
     */
    public void addContentHandler(ContentHandler handler) {
        m_contentHandlers.add(handler);
    }

    /**
     * Allows an application to remove a content event handler.
     * 
     * @param handler the Content Handler to be removed
     */
    public void removeContentHandler(ContentHandler handler) {
        if (! m_contentHandlers.contains(handler))
            System.out.println("handler not removable");
        else
            System.out.println("handler properly removed");
        m_contentHandlers.remove(handler);
    }

    /**
     * Parses a DXF data source.
     * 
     * @param reader the Reader of the DXF data source.
     */
    public void parse(Reader reader) throws TokenMgrError, ParseException {
        m_parser = new DXFParser(reader);
        m_parser.setContentHandler(new ContentHandler() {
            public void startSection(String section) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().startSection(section);
                }
            }
            public void endSection(String section) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().endSection(section);
                }
            }
            public void startTable(String name, Attributes atts) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().startTable(name, atts);
                }
            }
            public void endTable(String name) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().endTable(name);
                }
            }
            public void startBlock(String name, Attributes atts) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().startBlock(name, atts);
                }
            }
            public void endBlock(String name, Attributes atts) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().endBlock(name, atts);
                }
            }
            public void startElement(String name, Attributes atts) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().startElement(name, atts);
                }
            }
            public void endElement(String name) {
                Iterator<ContentHandler> it = m_contentHandlers.iterator();
                while (it.hasNext()) {
                    it.next().endElement(name);
                }
            }
        });
        m_parser.start();
    }
    public int getCurrentlyParsedLine() {
        if (m_parser == null || m_parser.token == null)
            return -1;
        return m_parser.token.beginLine;
    }
    public int getCurrentlyParsedColumn() {
        if (m_parser == null || m_parser.token == null)
            return -1;
        return m_parser.token.beginColumn;
    }
}