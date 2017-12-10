package xyz.kvantum.bukkit.objects;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.kvantum.bukkit.util.PlayerUtil;
import xyz.kvantum.server.api.util.SearchResultProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class PlayerManager implements SearchResultProvider<KvantumPlayer, KvantumPlayer>
{

    @Override
    public Collection<? extends KvantumPlayer> getResults(final KvantumPlayer kvantumPlayer)
    {
        final Collection<KvantumPlayer> kvantumPlayers = new ArrayList<>();
        if ( !kvantumPlayer.getUsername().isEmpty() )
        {
            final Player bukkitPlayer = Bukkit.getPlayer( kvantumPlayer.getUsername() );
            if ( bukkitPlayer != null )
            {
                kvantumPlayers.add( new KvantumPlayer( bukkitPlayer ) );
            } else
            {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer( kvantumPlayer.getUsername() );
                if ( offlinePlayer != null && offlinePlayer.hasPlayedBefore() )
                {
                    kvantumPlayers.add( new KvantumPlayer( offlinePlayer ) );
                }
            }
        } else if ( !kvantumPlayer.getUuid().isEmpty() )
        {
            UUID uuid = null;
            try
            {
                uuid = UUID.fromString( kvantumPlayer.getUuid() );
            } catch ( final Exception ignore ) {}
            if ( uuid != null )
            {
                final Player bukkitPlayer = Bukkit.getPlayer( uuid );
                if ( bukkitPlayer != null )
                {
                    kvantumPlayers.add( new KvantumPlayer( bukkitPlayer ) );
                } else
                {
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer( uuid );
                    if ( offlinePlayer != null && offlinePlayer.hasPlayedBefore() )
                    {
                        kvantumPlayers.add( new KvantumPlayer( offlinePlayer ) );
                    }
                }
            }
        } else if ( !kvantumPlayer.getWorld().isEmpty() )
        {
            final World world = Bukkit.getWorld( kvantumPlayer.getWorld() );
            if ( world != null )
            {
                for ( final Player player : world.getPlayers() )
                {
                    kvantumPlayers.add( new KvantumPlayer( player ) );
                }
            }
        } else
        {
            kvantumPlayers.addAll( PlayerUtil.getOnlinePlayers() );
        }
        return kvantumPlayers;
    }

}
