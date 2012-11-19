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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;


/** 
 * DXFWriter instances represent DXF files to be writen. Currently, no checking
 * of syntax or semantics is performed.
 * 
 * @author Berthold Firmenich
 * @version $Revision: 1717 $, $Author: svn-firmenich $
 * @version $Date: 2012-02-29 14:42:17 +0100 (Wed, 29 Feb 2012) $
 */
public class DXFWriter {

    private final static String EOL = "\r\n";
    private final static String CHARSET = "ISO-8859-1";

    private OutputStreamWriter m_out = null;
    private File m_file = null;
    private int m_handle = 0;

    protected DXFWriter() {
        // Disabled
    }
    /**
     * Opens the DXF file for writing.
     * 
     * @param file the file to be written
     * @throws IOException
     */
    public DXFWriter(File file) throws IOException {
        m_file = file;
        m_out = new OutputStreamWriter(new FileOutputStream(file), CHARSET);
    }
    
    /**
     * Writes the EOF group and closes the file. Updates the $HANDSEED variable.
     * 
     * @throws IOException
     */
    public void endFile() throws IOException {
        // Close file
        writeGroup(GroupCode.GC0, "EOF");
        m_out.close();
        
        // Update $HANDSEED
        String hdl = nextHandle();
        char[] chars = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int n = hdl.length();
        for (int i = 0; i < n; i++) {
            chars[chars.length-i-1] = hdl.charAt(n-i-1);
        }
        
        RandomAccessFile raf = new RandomAccessFile(m_file, "rw");
        String tag = raf.readLine();
        String data = raf.readLine();
        while (! (tag.equals("  0") && data.equals("ENDSEC"))) {
            if (tag.equals("  9") && data.equals("$HANDSEED")) {
                tag = raf.readLine();
                if (! tag.equals("  5"))
                    throw new AssertionError("$HANDSEED not found");
                long fp = raf.getFilePointer();
                data = raf.readLine();
                if (! data.equals("????????"))
                    throw new AssertionError("$HANDSEED not found");
                raf.seek(fp);
                raf.writeBytes(new String(chars));
                break;
            }
            tag = raf.readLine();
            data = raf.readLine();
        }
        raf.close();
    }
    
    /**
     * Starts a new section.
     * 
     * @param section the name of the section
     * @throws IOException
     */
    public void startSection(String section) throws IOException {
        writeGroup(GroupCode.GC0, "SECTION");
        writeGroup(GroupCode.GC2, section);
    }
    /**
     * Ends the current section.
     * 
     * @throws IOException
     */
    public void endSection() throws IOException {
        writeGroup(GroupCode.GC0, "ENDSEC");
    }

    /**
     * Generates the next unique handle.
     * 
     * @return the handle
     */
    public String nextHandle() {
        return Integer.toHexString(++m_handle).toUpperCase();
    }
    
    /**
     * Writes a group consisting of a group code and a String value.
     * 
     * @param gc the group code
     * @param val the value
     * @throws IOException
     */
    public void writeGroup(GroupCode gc, String val) throws IOException {
        String strgc = Integer.toString(gc.toInt());
        while (strgc.length() < 3)
            strgc = " " + strgc;
        m_out.write(strgc + EOL);
        m_out.write(val + EOL);
    }
    /**
     * Writes a group consisting of a group code and an int value.
     * 
     * @param gc the group code
     * @param val the value
     * @throws IOException
     */
    public void writeGroup(GroupCode gc, int val) throws IOException {
        String strval = Integer.toString(val);
        writeGroup(gc, strval);
    }
    /**
     * Writes a group consisting of a group code and a double value.
     * 
     * @param gc the group code
     * @param val the value
     * @throws IOException
     */
    public void writeGroup(GroupCode gc, double val) throws IOException {
        String strval = Double.isNaN(val) || Double.isInfinite(val) ? "0" :
            Double.toString(val);
        writeGroup(gc, strval);
    }
    /**
     * Writes a group consisting of a group code and a boolean value.
     * 
     * @param gc the group code
     * @param val the value
     * @throws IOException
     */
    public void writeGroup(GroupCode gc, boolean val) throws IOException {
        writeGroup(gc, val ? "1" : "0");
    }
}