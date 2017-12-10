package xyz.kvantum.bukkit;

import com.intellectualsites.configurable.ConfigurationFactory;
import lombok.Getter;
import xyz.kvantum.bukkit.config.BukkitConfig;
import xyz.kvantum.bukkit.views.SimpleWebPage;
import xyz.kvantum.server.api.config.CoreConfig;
import xyz.kvantum.server.api.logging.Logger;
import xyz.kvantum.server.api.views.staticviews.StaticViewManager;

import java.io.File;

/**
 * Basic implementation of {@link KvantumBukkit}
 */
@SuppressWarnings( "WeakerAccess" )
public final class SimpleKvantumBukkit extends KvantumBukkit
{

    @Getter
    private final File dataFolder;

    public SimpleKvantumBukkit(final File pluginDataFolder)
    {
        super( pluginDataFolder );
        this.dataFolder = pluginDataFolder;
    }

    @Override
    protected void doPreLoad()
    {
        CoreConfig.enableSecurityManager = false; // Doesn't work together with Bukkit!
        CoreConfig.verbose = false;
        CoreConfig.enableInputThread = false;
        CoreConfig.exitOnStop = false;
        CoreConfig.enablePlugins = false;
        ConfigurationFactory.load( BukkitConfig.class, getDataFolder(), new Object[0] ).get();
    }

    @Override
    protected void doPostLoad()
    {
        try
        {
            StaticViewManager.generate( getGetters() );
            if ( BukkitConfig.SimplePage.enableDefaultView )
            {
                StaticViewManager.generate( SimpleWebPage.class );
            }
        } catch ( final Exception e )
        {
            Logger.error( "Failed to create getters..." );
            e.printStackTrace();
        }
    }

    @Override
    protected void doPreStart()
    {
        Logger.info( "Starting Kvantum..." );
    }

    @Override
    protected void doPostStart()
    {
        super.doPostStart();
    }
}
