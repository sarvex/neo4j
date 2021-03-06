/**
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.modules;

import org.neo4j.kernel.configuration.Config;
import org.neo4j.kernel.logging.ConsoleLogger;
import org.neo4j.kernel.logging.Logging;
import org.neo4j.server.configuration.ServerSettings;
import org.neo4j.server.rest.dbms.AuthorizationFilter;
import org.neo4j.server.security.auth.AuthManager;
import org.neo4j.server.web.WebServer;

public class AuthorizationModule implements ServerModule
{
    private final WebServer webServer;
    private final Config config;
    private final AuthManager authManager;
    private final ConsoleLogger log;

    public AuthorizationModule( WebServer webServer, AuthManager authManager, Config config, Logging logging )
    {
        this.webServer = webServer;
        this.config = config;
        this.authManager = authManager;
        this.log = logging.getConsoleLog( getClass() );
    }

    @Override
    public void start()
    {
        if ( config.get( ServerSettings.auth_enabled ) )
        {
            webServer.addFilter( new AuthorizationFilter( authManager, log ), "/*" );
        }
    }

    @Override
    public void stop()
    {
    }
}
