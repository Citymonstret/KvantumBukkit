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

import com.intellectualsites.configurable.ConfigurationFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.kvantum.bukkit.config.BukkitConfig;
import xyz.kvantum.bukkit.objects.KvantumPlayer;
import xyz.kvantum.bukkit.objects.PlayerManager;
import xyz.kvantum.bukkit.views.SimpleWebPage;
import xyz.kvantum.bukkit.views.api.Getters;
import xyz.kvantum.server.api.config.CoreConfig;
import xyz.kvantum.server.api.core.Kvantum;
import xyz.kvantum.server.api.util.RequestManager;
import xyz.kvantum.server.api.views.rest.service.SearchService;
import xyz.kvantum.server.api.views.staticviews.StaticViewManager;
import xyz.kvantum.server.implementation.ServerContext;

import java.util.Optional;

public class KvantumPlugin extends JavaPlugin
{

    private final PlayerManager playerManager = new PlayerManager();
    private Kvantum kvantum;

    // View instances
    private final Getters getters = new Getters();

    @Override
    public void onLoad()
    {
        this.presetConfiguration();
        this.loadBukkitConfiguration();
        this.getLogger().info( "Loading Kvantum instance...." );
        final Optional<Kvantum> serverOptional = ServerContext.builder().coreFolder( getDataFolder() )
                .logWrapper( new BukkitLogWrapper() ).router( RequestManager.builder().build() )
                .standalone( true ).build().create();
        if ( !serverOptional.isPresent() )
        {
            this.getLogger().severe( "Failed to create Kvantum instance. Disabling!" );
            Bukkit.getPluginManager().disablePlugin( this );
            return;
        }
        kvantum = serverOptional.get();
        try
        {
            StaticViewManager.generate( getters );
            if ( BukkitConfig.SimplePage.enableDefaultView )
            {
                StaticViewManager.generate( SimpleWebPage.class );
            }
        } catch ( final Exception e )
        {
            getLogger().severe( "Failed to create getters..." );
            e.printStackTrace();
        }
    }

    private void loadBukkitConfiguration()
    {
        ConfigurationFactory.load( BukkitConfig.class, getDataFolder(), new Object[0] ).get();
    }

    @Override
    public void onEnable()
    {
        this.getLogger().info( "Starting Kvantum..." );

        SearchService.<KvantumPlayer, KvantumPlayer>builder().filter( "api/players" ).queryObjectType( KvantumPlayer
                .class ).resultProvider( playerManager ).build().registerHandler();

        this.kvantum.start();
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info( "Stopping kvantum" );
        this.kvantum.stopServer();
    }

    private void presetConfiguration()
    {
        CoreConfig.enableSecurityManager = false; // Doesn't work together with Bukkit!
        CoreConfig.verbose = false;
        CoreConfig.enableInputThread = false;
        CoreConfig.exitOnStop = false;
        CoreConfig.enablePlugins = false;
    }

}
