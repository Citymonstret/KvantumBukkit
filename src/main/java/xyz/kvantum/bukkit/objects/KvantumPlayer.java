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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.kvantum.server.api.orm.annotations.KvantumConstructor;
import xyz.kvantum.server.api.orm.annotations.KvantumField;
import xyz.kvantum.server.api.orm.annotations.KvantumInsert;
import xyz.kvantum.server.api.orm.annotations.KvantumObject;

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

    @KvantumConstructor
    public KvantumPlayer(@KvantumInsert( "username" ) final String username,
                         @KvantumInsert( "uuid") final String uuid,
                         @KvantumInsert( "world" ) final String world)
    {
        this.username = username;
        this.uuid = uuid;
        this.world = world;
        this.complete = false;
    }

    public KvantumPlayer(final Player bukkitPlayer)
    {
        this.username = bukkitPlayer.getDisplayName();
        this.uuid = bukkitPlayer.getUniqueId().toString();
        this.world = bukkitPlayer.getWorld().getName();
        this.complete = true;
        this.online = true;
    }

    public KvantumPlayer(final OfflinePlayer offlinePlayer)
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

}
