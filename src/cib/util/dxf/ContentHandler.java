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

/** 
 * Notifies about events while parsing a DXF source.
 * 
 * @author Berthold Firmenich
 * @version $Revision: 1717 $, $Author: svn-firmenich $
 * @version $Date: 2012-02-29 14:42:17 +0100 (Wed, 29 Feb 2012) $
 */
public interface ContentHandler {

    /**
     * 
     * @param section one of HEADER, CLASSES, TABLES, BLOCKS, ENTITIES, OBJECTS,
     *  THUMBNAILIMAGE
     */
    public void startSection(String section);
    
    /**
     * Receive notification of the end of a section.
     * 
     * @param section the same name as in startSection()
     */
    public void endSection(String section);
    
    /**
     * Receive notification of the table header. Each header is followed by
     * a sequence of tyble entries. For each table entry startElement() and
     * endElement() will be called subsequently. 
     * 
     * @param name in the form <TABLE> "@" <HANDLE>
     * @param atts the attributes of the table header
     */
    public void startTable(String name, Attributes atts);
    
    /**
     * Receive notification of the end of a table.
     * 
     * @param name in the form <TABLE> "@" <HANDLE>
     */
    public void endTable(String name);
    
    /**
     * Receive notification of the beginning of a block definition. Each block
     * definition contains the entities that make up the block. For each of
     * these entities startElement() and endElement() will be called
     * subsequently.
     * 
     * @param name the same name as in startTable()
     * @param atts the attributes of the block definition
     */
    public void startBlock(String name, Attributes atts);
    
    /**
     * Receive notification of the end of a block definition.
     * 
     * @param name the same name as in startBlock()
     * @param atts the attributes associated with the end of the block
     */
    public void endBlock(String name, Attributes atts);
    
    /**
     * Receive notification of the beginning of an element.
     * 
     * @param name a unique name
     * @param atts the attributes associated with the element
     */
    public void startElement(String name, Attributes atts);
    
    /**
     * Receive notification of the end of an element.
     * 
     * @param name the same name as in startElement()
     */
    public void endElement(String name);
}