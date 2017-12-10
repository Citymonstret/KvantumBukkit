package xyz.kvantum.bukkit.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.kvantum.bukkit.objects.KvantumPlayer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Various utilities involving both {@link KvantumPlayer} and {@link Player}
 */
@UtilityClass
@SuppressWarnings( { "unused", "WeakerAccess" } )
public class PlayerUtil
{

    /**
     * Get a collection containing kvantum representations of
     * all currently online Bukkit players
     *
     * @return Collection, will never be null
     */
    public static Collection<KvantumPlayer> getOnlinePlayers()
    {
        return Bukkit.getOnlinePlayers().stream().
                map( PlayerUtil::toKvantumPlayer ).collect( Collectors.toList() );
    }

    /**
     * Convert a bukkit player to a kvantum player
     *
     * @param bukkitPlayer Bukkit player
     * @return Kvantum representation
     */
    public static KvantumPlayer toKvantumPlayer(@NonNull final Player bukkitPlayer)
    {
        return new KvantumPlayer( bukkitPlayer );
    }

    /**
     * Try to convert a kvantum player to a bukkit player
     *
     * @param player Kvantum representation
     * @return Bukkit player
     */
    public static Optional<Player> toBukkitPlayer(@NonNull final KvantumPlayer player)
    {
        final Player bukkitPlayer = Bukkit.getPlayer( UUID.fromString( player.getUuid() ) );
        return Optional.ofNullable( bukkitPlayer );
    }

}
