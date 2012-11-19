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
 * Instances of this class represent the Object Coordinate System (OCS).
 *
 * @author Berthold Firmenich
 * @version $Revision: 1717 $, $Author: svn-firmenich $
 * @version $Date: 2012-02-29 14:42:17 +0100 (Wed, 29 Feb 2012) $
 */

public class ObjectCoordinateSystem {

    private double[][] m_ocs2wcs = null;    // OCS to WCS transform

    /**
     * Instances of this class represent an Object Coordinate System (OCS).
     *
     * @param atts Attributes of the entity that defines the OCS
     */
    public ObjectCoordinateSystem(Attributes atts) {

        // Nothing to do if OCS is off
        if (! atts.valueExists(GroupCode.GC210)) {
            m_ocs2wcs = null;
            return;
        }

        // Calculate the object coordinate base OCS (ax, ay, az)
        double[] az = new double[3];    // Only the normal vector is given!
        az[0] = atts.getDoubleValue(GroupCode.GC210);
        az[1] = atts.getDoubleValue(GroupCode.GC220);
        az[2] = atts.getDoubleValue(GroupCode.GC230);

        double[] ax = new double[3];
        double[] ay = new double[3];
        calculateArbitraryAxes(az, ax, ay);

        /*
         * Setup the transformation from OCS (ax, ay, az) to WCS (wx, wy, wz)
         * according to an algorithm described in Foley/ van Dam, where three
         * given points P1, P2 and P3 are transformed so that:
         *    P1' is on the origin
         *    P2' is on the positive z-axis
         *    P3' is in the positive y-axis half of the (y, z)-plane
         *
         *     +-           -+
         *     | r1x r2x r3x |
         * R = | r1y r2y r3y |
         *     | r1z r2z r3z |
         *     +-           -+
         *
         * where
         *          +-   -+
         *          | r1z |     P1 P2
         *     Rz = | r2z | = ---------
         *          | r3z |   | P1 P2 |
         *          +-   -+
         *
         *          +-   -+
         *          | r1x |     P1 P3 x P1 P2
         *     Rx = | r2x | = -----------------
         *          | r3x |   | P1 P3 x P1 P2 |
         *          +-   -+
         *
         *          +-   -+
         *          | r1y |
         *     Ry = | r2y | = Rz x Rx
         *          | r3y |
         *          +-   -+
         *
         * In our case the three points are
         *    P1 = (0, 0, 0)
         *    P2 = (az1, az2, az3)
         *    P3 = (ay1, ay2, ay3)
         *
         */

        double[] rz = new double[] {az[0], az[1], az[2]};
        normalize(rz, rz);

        double[] rx = new double[3];
        cross(ay, az, rx);
        normalize(rx, rx);

        double[] ry = new double[3];
        cross(rz, rx, ry);

        // Invert ...
        double[][] mat = new double[][] {
                {rx[0], rx[1], rx[2]},
                {ry[0], ry[1], ry[2]},
                {rz[0], rz[1], rz[2]}
        };
        invert(mat, mat);

        // ... and store the matrix as attribute
        m_ocs2wcs = mat;
    }

    /**
     * Transforms object coordinates to world coordinates.
     *
     * @param ox the OCS x coordinate
     * @param oy the OCS y coordinate
     * @param oz the OCS z coordinate
     * @param wcrds the WCS coordinates. The array must have a size of 3
     *     entries.
     */
    public void toWCS(double ox, double oy, double oz, double[] wcrds) {
        if (m_ocs2wcs == null) {
            wcrds[0] = ox;
            wcrds[1] = oy;
            wcrds[2] = oz;
        }
        else {
            wcrds[0] = m_ocs2wcs[0][0] * ox + m_ocs2wcs[0][1] * oy +
                m_ocs2wcs[0][2] * oz;
            wcrds[1] = m_ocs2wcs[1][0] * ox + m_ocs2wcs[1][1] * oy +
                m_ocs2wcs[1][2] * oz;
            wcrds[2] = m_ocs2wcs[2][0] * ox + m_ocs2wcs[2][1] * oy +
                m_ocs2wcs[2][2] * oz;
        }
    }

    /**
     * Gets the x coordinate in WCS of a given object coordinate in OCS.
     *
     * @param ox the OCS x coordinate
     * @param oy the OCS y coordinate
     * @param oz the OCS z coordinate
     * @return the WCS x coordinate
     */
    public double getXinWCS(double ox, double oy, double oz) {
        return m_ocs2wcs == null ?
            ox :
            m_ocs2wcs[0][0] * ox + m_ocs2wcs[0][1] * oy + m_ocs2wcs[0][2] * oz;
    }

    /**
     * Gets the y coordinate in WCS of a given object coordinate in OCS.
     *
     * @param ox the OCS x coordinate
     * @param oy the OCS y coordinate
     * @param oz the OCS z coordinate
     * @return the WCS y coordinate
     */
    public double getYinWCS(double ox, double oy, double oz) {
        return m_ocs2wcs == null ?
            oy :
            m_ocs2wcs[1][0] * ox + m_ocs2wcs[1][1] * oy + m_ocs2wcs[1][2] * oz;
    }

    /**
     * Gets the z coordinate in WCS of a given object coordinate in OCS.
     *
     * @param ox the OCS x coordinate
     * @param oy the OCS y coordinate
     * @param oz the OCS z coordinate
     * @return the WCS z coordinate
     */
    public double getZinWCS(double ox, double oy, double oz) {
        return m_ocs2wcs == null ?
            oz :
            m_ocs2wcs[2][0] * ox + m_ocs2wcs[2][1] * oy + m_ocs2wcs[2][2] * oz;
    }

    /**
     * Performs the Arbitrary Axis Algorithm for a given normal vector.
     *
     * @param az the normal vector to be given
     * @param ax the vector ax
     * @param ay the vector ay
     */
    private static void calculateArbitraryAxes(double[] az, double[] ax,
    double[] ay) {
        final double[] WY = new double[] {0, 1, 0};
        final double[] WZ = new double[] {0, 0, 1};

        // Calculate the arbitrary axes ax, ay (both are normal to n)
        final double LIMIT = 1. / 64.;
        if (Math.abs(az[0]) < LIMIT && Math.abs(az[1]) < LIMIT)
            cross(WY, az, ax);   // ax = wy x n
        else
            cross(WZ, az, ax);   // ax = wz x n;
        normalize(ax, ax);

        cross(az, ax, ay);       // ay = n x ax
        normalize(ay, ay);
    }

    private static void cross(double[] a, double[] b, double[] c) {
        double c0 = a[1] * b[2] - a[2] * b[1];
        double c1 = a[2] * b[0] - a[0] * b[2];
        double c2 = a[0] * b[1] - a[1] * b[0];
        c[0] = c0;
        c[1] = c1;
        c[2] = c2;
    }
    private static void normalize(double[] in, double[] out) {
        double len = length(in);
        out[0] = in[0] / len;
        out[1] = in[1] / len;
        out[2] = in[2] / len;
    }
    private static double length(double[] a) {
        return Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    }
    private static double det(double[][] mat) {
        return mat[0][0] * (mat[1][1] * mat[2][2] - mat[2][1] * mat[1][2]) -
        mat[0][1] * (mat[1][0] * mat[2][2] - mat[2][0] * mat[1][2]) +
        mat[0][2] * (mat[1][0] * mat[2][1] - mat[2][0] * mat[1][1]);
    }
    private static void invert(double[][] mat, double[][] inv) {
        double _00 = mat[1][1] * mat[2][2] - mat[1][2] * mat[2][1];
        double _01 = mat[0][2] * mat[2][1] - mat[0][1] * mat[2][2];
        double _02 = mat[0][1] * mat[1][2] - mat[0][2] * mat[1][1];

        double _10 = mat[1][2] * mat[2][0] - mat[1][0] * mat[2][2];
        double _11 = mat[0][0] * mat[2][2] - mat[0][2] * mat[2][0];
        double _12 = mat[0][2] * mat[1][0] - mat[0][0] * mat[1][2];

        double _20 = mat[1][0] * mat[2][1] - mat[1][1] * mat[2][0];
        double _21 = mat[0][1] * mat[2][0] - mat[0][0] * mat[2][1];
        double _22 = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];

        double d = det(mat);

        inv[0][0] = _00 / d; inv[0][1] = _01 / d; inv[0][2] = _02 / d;
        inv[1][0] = _10 / d; inv[1][1] = _11 / d; inv[1][2] = _12 / d;
        inv[2][0] = _20 / d; inv[2][1] = _21 / d; inv[2][2] = _22 / d;
    }
}