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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import cib.util.dxf.interpreter.Token;

/** 
 * Implementation of the Attributes interface.
 * 
 * @author Berthold Firmenich
 * @version $Revision: 1721 $, $Author: svn-firmenich $
 * @version $Date: 2012-03-13 17:37:22 +0100 (Tue, 13 Mar 2012) $
 */
public class AttributesAdapter implements Attributes {
    private List<DXFDataElement> m_list = new ArrayList<DXFDataElement>();
    private Map<GroupCode,List<String>> m_map =
        new HashMap<GroupCode,List<String>>();
    private int m_firstParsedLine = -1;
    private int m_lastParsedLine = -1;
    
    private class DXFDataElement {
        private GroupCode m_gc;
        private String m_val;
        
        private DXFDataElement(GroupCode gc, String val) {
            m_gc = gc;
            m_val = val;
        }
        private DXFDataElement(Token token) {
            int i = token.image.indexOf("\r\n");
            if (i != -1) {
                m_val = token.image.substring(i + 2, token.image.length() - 2);
            }
            else {
                i = token.image.indexOf("\n");
                m_val = token.image.substring(i + 1, token.image.length() - 1);
            }
            
            String gc = token.image.substring(0, i);
            int j = gc.lastIndexOf(" ");
            gc = gc.substring(j + 1);
            m_gc = GroupCode.valueOf("GC" + gc);
        }
    }

    /**
     * Returns the first parsed line of these attributes.
     * 
     * @return the first parsed line
     */
    public int firstParsedLine() {
        return m_firstParsedLine;
    }

    /**
     * Returns the last parsed line of these attributes.
     * 
     * @return the last parsed line
     */
    public int lastParsedLine() {
        return m_lastParsedLine;
    }

    /**
     * Clears all attributes.
     *
     */
    public void clear() {
        m_list.clear();
        m_map.clear();
    }

    /**
     * Stores an attribute from a Token.
     * 
     * @param token the Token
     */
    public void add(Token token){
        DXFDataElement de = new DXFDataElement(token);
        m_list.add(de);
        if (! m_map.containsKey(de.m_gc))
            m_map.put(de.m_gc, new ArrayList<String>());
        List<String> values = m_map.get(de.m_gc);
        values.add(de.m_val);
    }
    
    /**
     * Stores an attribute from a group code and its value.
     * 
     * @param gc Group code
     * @param val value
     */
    public void add(GroupCode gc, String val) {
        DXFDataElement de = new DXFDataElement(gc, val);
        m_list.add(de);
        if (! m_map.containsKey(de.m_gc))
            m_map.put(de.m_gc, new ArrayList<String>());
        List<String> values = m_map.get(de.m_gc);
        values.add(de.m_val);
    }

    public int size() {
        return m_list.size();
    }
    public GroupCode getGroupCodeAt(int index) {
        if (index < 0 || index >= m_list.size())
            throw new NoSuchElementException("No element at index " + index);
        DXFDataElement de = m_list.get(index);
        return de.m_gc;
    }
    public String getValueAt(int index) {
        if (index < 0 || index >= m_list.size())
            throw new NoSuchElementException("No element at index " + index);
        DXFDataElement de = m_list.get(index);
        return de.m_val;
    }
    
    public int getIntValueAt(int index) {
        String str = getValueAt(index);
        return Integer.parseInt(str.trim());
    }
    
    public double getDoubleValueAt(int index) {
        String str = getValueAt(index);
        return Double.parseDouble(str.trim());
    }
    
    public boolean getBooleanValueAt(int index) {
        String str = getValueAt(index);
        return Boolean.parseBoolean(str.trim());
    }
    
    public int getValueSize(GroupCode code) {
        if (! m_map.containsKey(code))
            return 0;
        List<String> values = m_map.get(code);
        return values.size();
    }

    public boolean valueExists(GroupCode code) {
        return getValueSize(code) > 0;
    }
    public String getValue(GroupCode code) {
        return getValue(0, code);
    }
    public String getValue(GroupCode code, String def) {
        return getValue(0, code, def);
    }
    public int getIntValue(GroupCode code) {
        return getIntValue(0, code);
    }
    public int getIntValue(GroupCode code, int def) {
        return getIntValue(0, code, def);
    }
    public double getDoubleValue(GroupCode code) {
        return getDoubleValue(0, code);
    }
    public double getDoubleValue(GroupCode code, double def) {
        return getDoubleValue(0, code, def);
    }
    public boolean getBooleanValue(GroupCode code) {
        return getBooleanValue(0, code);
    }
    public boolean getBooleanValue(GroupCode code, boolean def) {
        return getBooleanValue(0, code, def);
    }

    public boolean valueExists(int index, GroupCode code) {
        return index >= 0 && index < getValueSize(code);
    }
    public String getValue(int index, GroupCode code) {
        try {
            List<String> values = m_map.get(code);
            return values.get(index);
        }
        catch (Exception e) {
            throw new NoSuchElementException("No value for Group Code " + code +
                " at index " + index);
        }
    }
    public String getValue(int index, GroupCode code, String def) {
        try {
            return getValue(index, code);
        }
        catch (NoSuchElementException e) {
        }
        return def;
    }

    public int getIntValue(int index, GroupCode code) {
        String str = getValue(index, code);
        return Integer.parseInt(str.trim());
    }
    public int getIntValue(int index, GroupCode code, int def) {
        try {
            String str = getValue(index, code);
            return Integer.parseInt(str.trim());
        }
        catch (NoSuchElementException e) {
        }
        return def;
    }

    public double getDoubleValue(int index, GroupCode code) {
        String str = getValue(index, code);
        return Double.parseDouble(str.trim());
    }
    public double getDoubleValue(int index, GroupCode code, double def) {
        try {
            String str = getValue(index, code);
            return Double.parseDouble(str.trim());
        }
        catch (NoSuchElementException e) {
        }
        return def;
    }

    public boolean getBooleanValue(int index, GroupCode code) {
        String str = getValue(index, code);
        return Boolean.parseBoolean(str.trim());
    }
    public boolean getBooleanValue(int index, GroupCode code, boolean def) {
        try {
            String str = getValue(index, code);
            return Boolean.parseBoolean(str.trim());
        }
        catch (NoSuchElementException e) {
        }
        return def;
    }

    public void setFirstParsedLine(int line) {
        m_firstParsedLine = line;
    }

    public void setLastParsedLine(int line) {
        m_lastParsedLine = line;
    }
}
