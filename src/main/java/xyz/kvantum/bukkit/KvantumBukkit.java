package xyz.kvantum.bukkit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import xyz.kvantum.bukkit.objects.KvantumPlayer;
import xyz.kvantum.bukkit.objects.PlayerManager;
import xyz.kvantum.bukkit.views.api.Getters;
import xyz.kvantum.server.api.core.Kvantum;
import xyz.kvantum.server.api.logging.Logger;
import xyz.kvantum.server.api.util.RequestManager;
import xyz.kvantum.server.api.views.rest.service.SearchService;
import xyz.kvantum.server.implementation.ServerContext;

import java.io.File;
import java.util.Optional;

/**
 * Base implementation class, override this to
 * implement a custom web service running alongside
 * the Bukkit server instance
 */
@RequiredArgsConstructor
@SuppressWarnings( { "unused", "WeakerAccess" } )
public abstract class KvantumBukkit
{

    @Getter
    private final PlayerManager playerManager = new PlayerManager();
    @Getter( AccessLevel.PROTECTED )
    private final Getters getters = new Getters();
    @Getter
    private final Kvantum kvantum;

    /**
     * Main constructor
     *
     * @param coreFolder Main folder
     */
    public KvantumBukkit(@NonNull final File coreFolder)
    {
        this.doPreLoad();
        final Optional<Kvantum> serverOptional = ServerContext.builder().coreFolder( coreFolder )
                .logWrapper( new BukkitLogWrapper() ).router( RequestManager.builder().build() )
                .standalone( true ).build().create();
        if ( !serverOptional.isPresent() )
        {
            Logger.error( "Failed to create Kvantum instance. Disabling!" );
            this.onFailure();
            this.kvantum = null;
        } else
        {
            this.kvantum = serverOptional.get();
        }
        this.doPostLoad();
    }

    /**
     * Start the server, blocks until the server is started
     */
    public final void start()
    {
        this.doPreStart();
        SearchService.<KvantumPlayer, KvantumPlayer>builder().filter( "api/players" ).queryObjectType( KvantumPlayer
                .class ).resultProvider( playerManager ).build().registerHandler();
        this.kvantum.start();
        this.doPostStart();
    }

    /**
     * Stop the server, blocks until all threads and closed
     */
    public final void stop()
    {
        this.kvantum.stopServer();
    }

    /**
     * Called when the server fails to start
     */
    protected void onFailure()
    {
        // Override me!
    }

    /**
     * Called before the Kvantum instance is loaded
     */
    protected void doPreLoad()
    {
        // Override me!
    }

    /**
     * Called after the Kvantum instance is loaded
     */
    protected void doPostLoad()
    {
        // Override me!
    }

    /**
     * Called before the server has started
     */
    protected void doPreStart()
    {
        // Override me!
    }

    /**
     * Called after the server has started
     */
    protected void doPostStart()
    {
        // Override me!
    }
}
