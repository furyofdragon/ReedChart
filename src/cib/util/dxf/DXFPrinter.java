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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import cib.util.dxf.interpreter.ParseException;

/** 
 * Parses a DXF source and prints events to the sandard output stream.
 * 
 * @author Berthold Firmenich
 * @version $Revision: 1717 $, $Author: svn-firmenich $
 * @version $Date: 2012-02-29 14:42:17 +0100 (Wed, 29 Feb 2012) $
 */
public class DXFPrinter {
    private static String s_indent = "";
    private static void incIndent() {
        s_indent += "    ";
    }
    private static void decIndent() {
        s_indent = s_indent.substring(4);
    }
    private static String getIndent() {
        return s_indent;
    }
    
    public static void main(String[] args) throws ParseException {
        // Usage
        if (args.length != 1) {
            System.out.println("Usage: DXFPrinter filename");
            System.exit(0);
        }

        // Setup DXF Reader
        final DXFReader myDXFReader = new DXFReader();
        myDXFReader.addContentHandler(new ContentHandler() {
            // Receive notification of the beginning of a section.
            public void startSection(String section) {
                System.out.println("SECTION: " + section);
                incIndent();
            }
            
            // Receive notification of the end of a section.
            public void endSection(String section) {
                decIndent();
            }
            
            // Receive notification of the beginning of a table.
            public void startTable(String name, Attributes atts) {
                System.out.println(getIndent() + name + ": lines " +
                    atts.firstParsedLine() + "-" + atts.lastParsedLine());
                incIndent();
                int n = atts.size();
                for (int i = 0; i < n; i++)
                    System.out.println(getIndent() + atts.getGroupCodeAt(i) +
                        ": " + atts.getValueAt(i));
            }
            
            // Receive notification of the end of a table.
            public void endTable(String name) {
                decIndent();
            }
            
            // Receive notification of the beginning of a block.
            public void startBlock(String name, Attributes atts) {
                System.out.println(getIndent() + name + ": lines " +
                    atts.firstParsedLine() + "-" + atts.lastParsedLine());
                incIndent();
                int n = atts.size();
                for (int i = 0; i < n; i++)
                    System.out.println(getIndent() + atts.getGroupCodeAt(i) +
                        ": " + atts.getValueAt(i));
            }
            
            // Receive notification of the end of a block.
            public void endBlock(String name, Attributes atts) {
                decIndent();
            }
            
            // Receive notification of the beginning of an element.
            public void startElement(String name, Attributes atts) {
                System.out.println(getIndent() + name + ": lines " +
                    atts.firstParsedLine() + "-" + atts.lastParsedLine());
                incIndent();
                int n = atts.size();
                for (int i = 0; i < n; i++)
                    System.out.println(getIndent() + atts.getGroupCodeAt(i) +
                        ": " + atts.getValueAt(i));
            }
            
            // Receive notification of the end of an element.
            public void endElement(String name) {
                decIndent();
            }
        });

        // Parse file
        FileInputStream myStream = null;
        InputStreamReader myReader = null;
        try {
            System.out.println("DXFPrinter: parsing file " + args[0]);
            myStream = new FileInputStream(new File(args[0]));
            // CharSet      Description
            //
            // US-ASCII     Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic
            //              Latin block of the Unicode character set
            // ISO-8859-1   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
            // UTF-8        Eight-bit UCS Transformation Format
            // UTF-16BE     Sixteen-bit UCS Transformation Format, big-endian
            //              byte order
            // UTF-16LE     Sixteen-bit UCS Transformation Format, little-endian
            //              byte order
            // UTF-16       Sixteen-bit UCS Transformation Format, byte order
            //              identified by an optional byte-order mark
            myReader = new InputStreamReader(myStream, "ISO-8859-1");
        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        
        //myDXFReader.parse(myStream);
        myDXFReader.parse(myReader);
    }
}
