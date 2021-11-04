/*
    k-dimensional Weisfeiler-Lehman for Group Isomorphism, a Java implementation
    of the Weisfeiler-Lehman combinatorial method with various launch
    configurations to test, analyze the method, as well as gathering info
    from running the method on finite groups. The implementation currently
    supports 2-dimensional Weisfeiler-Lehman and is planned to have support
    for any number of dimensions in the future (hence the name "k-dimensional").
    Copyright (C) 2021 Karim Elsheikh

    This file is part of k-dimensional Weisfeiler-Lehman for Group Isomorphism,
    the Java project.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

/**
 * group.data contains classes that store all the data that is generated while
 * the WL combinatorial method is running on a group/pair of groups.
 * 
 * @author Karim Elsheikh
 */
package group.data;