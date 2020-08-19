/*
 * LDAP Chai API
 * Copyright (c) 2006-2017 Novell, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.novell.ldapchai.provider;

import com.novell.ldapchai.exception.ChaiRuntimeException;

import java.io.Serializable;

class SettingValidator
{
    static final Validator INTEGER_VALIDATOR = value ->
    {
        try
        {
            Integer.parseInt( value );
        }
        catch ( Exception e )
        {
            throw new IllegalArgumentException( e.getMessage() );
        }
    };

    static final Validator BOOLEAN_VALIDATOR = value ->
    {
        try
        {
            Boolean.parseBoolean( value );
        }
        catch ( Exception e )
        {
            throw new IllegalArgumentException( e.getMessage() );
        }
    };

    static final Validator AUTO_OR_BOOLEAN_VALIDATOR = value ->
    {
        if ( !"auto".equalsIgnoreCase( value ) )
        {
            try
            {
                Boolean.parseBoolean( value );
            }
            catch ( Exception e )
            {
                throw new IllegalArgumentException( e.getMessage() );
            }
        }
    };

    static final Validator ENABLE_NMAS_VALIDATOR = value ->
    {
        try
        {
            final boolean enableNmas = Boolean.parseBoolean( value );

            if ( enableNmas )
            {
                try
                {
                    Class.forName( "com.novell.security.nmas.mgmt.NMASChallengeResponse" );
                }
                catch ( ClassNotFoundException ex )
                {
                    final String errorMsg = "Error: Unable to enable NMAS support. One or more classes"
                            + " could not be loaded from the NMAS Toolkit.  Check to make sure NMASToolkit.jar"
                            + " is available on the classpath.";
                    throw new ChaiRuntimeException( errorMsg );
                }
            }
        }
        catch ( Exception e )
        {
            throw new IllegalArgumentException( e.getMessage() );
        }
    };


    interface Validator extends Serializable
    {
        void validate( String value );
    }
}
