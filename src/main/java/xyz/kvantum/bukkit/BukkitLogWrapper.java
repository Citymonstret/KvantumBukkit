/*
 *    Copyright (C) 2017 Kvantum
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
package xyz.kvantum.bukkit;

import org.apache.commons.text.StrSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import xyz.kvantum.server.api.config.CoreConfig;
import xyz.kvantum.server.api.core.ServerImplementation;
import xyz.kvantum.server.api.logging.LogContext;
import xyz.kvantum.server.api.logging.LogWrapper;
import xyz.kvantum.server.api.util.Assert;
import xyz.kvantum.server.implementation.Server;

import java.io.PrintStream;
import java.lang.reflect.Field;

class BukkitLogWrapper implements LogWrapper
{

    private PrintStream logStream;

    private PrintStream getPrintStream()
    {
        if ( logStream != null )
        {
            return this.logStream;
        }
        if ( ServerImplementation.hasImplementation() )
        {
            final Server server = ( Server ) ServerImplementation.getImplementation();
            final Field field;
            try
            {
                field = server.getClass().getDeclaredField( "logStream" );
                field.setAccessible( true );
                this.logStream = ( PrintStream ) field.get( server );
            } catch ( final NoSuchFieldException | IllegalAccessException e )
            {
                e.printStackTrace();
            }
        }
        return logStream;
    }

    @Override
    public void setFormat(String s)
    {
    }

    @Override
    public void log(final LogContext logContext)
    {
        Assert.notNull( logContext );

        String replacedMessage = StrSubstitutor.replace( CoreConfig.Logging.logFormat, logContext.toMap() );
        if ( getPrintStream() != null )
        {
            getPrintStream().println( replacedMessage );
        }

        Bukkit.getConsoleSender()
                .sendMessage( ChatColor.translateAlternateColorCodes( '&', replacedMessage ) );
    }

    @Override
    public void log(String s)
    {
        Bukkit.getConsoleSender().sendMessage( "Web > " + s );
    }

    @Override
    public void breakLine()
    {
        Bukkit.getConsoleSender().sendMessage( "" );
    }
}
