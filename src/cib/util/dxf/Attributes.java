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

import java.util.NoSuchElementException;

/** 
 * Represents the attributes of an entity.
 * 
 * @author Berthold Firmenich
 * @version $Revision: 1717 $, $Author: svn-firmenich $
 * @version $Date: 2012-02-29 14:42:17 +0100 (Wed, 29 Feb 2012) $
 */
public interface Attributes {
    /**
     * Returns the first parsed line of these attributes.
     * 
     * @return the first parsed line
     */
    public int firstParsedLine();

    /**
     * Returns the last parsed line of these attributes.
     * 
     * @return the last parsed line
     */
    public int lastParsedLine();

    /**
     * Returns the number of all data elements of an entity.
     * 
     * @return the number of data elements
     */
    public int size();
    
    /**
     * Returns the Group Code at a null based index.
     * 
     * @param index the index of the Group Code (0 <= index < size())
     * @return the Group Code
     */
    public GroupCode getGroupCodeAt(int index) throws NoSuchElementException;
    
    /**
     * Returns the value (the data element) at a null based index.
     * 
     * @param index the index of the data element (0 <= index < size())
     * @return the data element
     */
    public String getValueAt(int index) throws NoSuchElementException;
    
    /**
     * Returns the int data element at a null based index.
     * 
     * @param index the index of the data element (0 <= index < size())
     * @return the data element
     */
    public int getIntValueAt(int index) throws NoSuchElementException;
    
    /**
     * Returns the double data element at a null based index.
     * 
     * @param index the index of the data element (0 <= index < size())
     * @return the data element
     */
    public double getDoubleValueAt(int index) throws NoSuchElementException;
    
    /**
     * Returns the boolean data element at a null based index.
     * 
     * @param index the index of the data element (0 <= index < size())
     * @return the data element
     */
    public boolean getBooleanValueAt(int index) throws NoSuchElementException;
    
    /**
     * Returns the number of data elements (values) of a Group Code.
     * 
     * @param code the Group Code
     * @return the number of data elements
     */
    public int getValueSize(GroupCode code);
    
    /**
     * Checks whether the value of a Group Code exists.
     * 
     * @param code the Group Code
     * @return true if the value exists
     */
    public boolean valueExists(GroupCode code);
    
    /**
     * Returns the first data element of a Group Code 
     * 
     * @param code the Group Code
     * @return the data element (value)
     */
    public String getValue(GroupCode code) throws NoSuchElementException;
    
    /**
     * Returns the first data element of a Group Code or a default value if it
     * doesn't exist.
     * 
     * @param code the Group Code
     * @param def the default value
     * @return the data element (value)
     */
    public String getValue(GroupCode code, String def);
    
    /**
     * Returns the first data element of a Group Code 
     * 
     * @param code the Group Code
     * @return the data element (value)
     */
    public int getIntValue(GroupCode code) throws NoSuchElementException;

    /**
     * Returns the first int element of a Group Code or a default value if it
     * doesn't exist.
     * 
     * @param code the Group Code
     * @param def the default value
     * @return the data element (value)
     */
    public int getIntValue(GroupCode code, int def);
    
    /**
     * Returns the first double element of a Group Code 
     * 
     * @param code the Group Code
     * @return the data element (value)
     */
    public double getDoubleValue(GroupCode code) throws NoSuchElementException;
    
    /**
     * Returns the first double element of a Group Code or a default if it
     * doesn't exist 
     * 
     * @param code the Group Code
     * @param def the default value
     * @return the data element (value)
     */
    public double getDoubleValue(GroupCode code, double def);
    
    /**
     * Returns the first boolean element of a Group Code.
     * 
     * @param code the Group Code
     * @return the data element (value)
     */
    public boolean getBooleanValue(GroupCode code)
    throws NoSuchElementException;
    
    /**
     * Returns the first boolean element of a Group Code or a default value if
     * it doesn't exist.
     * 
     * @param code the Group Code
     * @param def the default value
     * @return the data element (value)
     */
    public boolean getBooleanValue(GroupCode code, boolean def);
    
    /**
     * Checks whether the value of a specified index of a Group Code exists.
     * 
     * @param code the Group Code
     * @return true if the value exists
     */
    public boolean valueExists(int index, GroupCode code);
    
    /**
     * Return the data element at a specified index of a Group Code
     * 
     * @param index the null based index
     * @param code the Group Code
     * @return the data element (value)
     */
    public String getValue(int index, GroupCode code)
    throws NoSuchElementException;

    /**
     * Returns the String element at a specified index of a Group Code or a 
     * default value if it doesn't exist.
     * 
     * @param index the null based index
     * @param code the Group Code
     * @param def the default value
     * @return the data element (value)
     */
    public String getValue(int index, GroupCode code, String def);

    /**
     * Return the int data element at a specified index of a Group Code
     * 
     * @param index the null based index
     * @param code the Group Code
     * @return the int data element
     */
    public int getIntValue(int index, GroupCode code)
    throws NoSuchElementException;

    /**
     * Return the int data element at a specified index of a Group Code or a 
     * default value if it doesn't exist.
     * 
     * @param index the null based index
     * @param code the Group Code
     * @param def the default value
     * @return the int data element
     */
    public int getIntValue(int index, GroupCode code, int def);

    /**
     * Return the double data element at a specified index of a Group Code
     * 
     * @param index the null based index
     * @param code the Group Code
     * @return the double data element
     */
    public double getDoubleValue(int index, GroupCode code)
    throws NoSuchElementException;

    /**
     * Return the double data element at a specified index of a Group Code or a 
     * default value if it doesn't exist.
     * 
     * @param index the null based index
     * @param code the Group Code
     * @param def the default value
     * @return the double data element
     */
    public double getDoubleValue(int index, GroupCode code, double def);

    /**
     * Return the boolean data element at a specified index of a Group Code
     * 
     * @param index the null based index
     * @param code the Group Code
     * @return the boolean value
     */
    public boolean getBooleanValue(int index, GroupCode code)
    throws NoSuchElementException;

    /**
     * Return the boolean data element at a specified index of a Group Code or a 
     * default value if it doesn't exist.
     * 
     * @param index the null based index
     * @param code the Group Code
     * @param def the default value
     * @return the boolean value
     */
    public boolean getBooleanValue(int index, GroupCode code, boolean def);
}
