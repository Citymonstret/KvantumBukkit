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
package xyz.kvantum.bukkit.objects;

import edu.umd.cs.findbugs.annotations.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import xyz.kvantum.server.api.orm.annotations.KvantumConstructor;
import xyz.kvantum.server.api.orm.annotations.KvantumField;
import xyz.kvantum.server.api.orm.annotations.KvantumInsert;
import xyz.kvantum.server.api.orm.annotations.KvantumObject;

/**
 * Kvantum representation of Bukkit {@link Player}
 */
@EqualsAndHashCode
@KvantumObject
public class KvantumPlayer
{

    @KvantumField( defaultValue = "" )
    @Getter
    private String username;

    @KvantumField( defaultValue = "" )
    @Getter
    private String uuid;

    @KvantumField( defaultValue = "" )
    @Getter
    private String world;

    @Getter
    private final boolean complete;

    @Getter
    private boolean online;

    /**
     * Kvantum constructor
     *
     * @param username nullable player username
     * @param uuid nullable player uuid
     * @param world nullable player world
     */
    @KvantumConstructor
    public KvantumPlayer(@Nullable @KvantumInsert( "username" ) final String username,
                         @Nullable @KvantumInsert( "uuid") final String uuid,
                         @Nullable @KvantumInsert( "world" ) final String world)
    {
        this.username = username;
        this.uuid = uuid;
        this.world = world;
        this.complete = false;
    }

    /**
     * Construct a Kvantum player from a Bukkit player
     *
     * @param bukkitPlayer Bukkit player
     */
    public KvantumPlayer(@NonNull final Player bukkitPlayer)
    {
        this.username = bukkitPlayer.getDisplayName();
        this.uuid = bukkitPlayer.getUniqueId().toString();
        this.world = bukkitPlayer.getWorld().getName();
        this.complete = true;
        this.online = true;
    }

    /**
     * Construct a Kvantum player from a Bukkit offline player
     *
     * @param offlinePlayer Bukkit offline player
     */
    public KvantumPlayer(@NonNull final OfflinePlayer offlinePlayer)
    {
        this.username = offlinePlayer.getName();
        this.uuid = offlinePlayer.getUniqueId().toString();
        final Location location = offlinePlayer.getBedSpawnLocation();
        if ( location != null )
        {
            this.world = location.getWorld().getName();
        } else
        {
            this.world = Bukkit.getWorlds().get( 0 ).getName();
        }
        this.complete = true;
        this.online = false;
    }

    /**
     * Get a JSON object representing the player
     *
     * @return JSON object
     */
    public JSONObject toJson()
    {
        final JSONObject object = new JSONObject();
        object.put( "username", this.username );
        object.put( "uuid", this.uuid );
        object.put( "online", this.online );
        object.put( "world", this.world );
        return object;
    }

}
