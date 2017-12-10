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

import org.bukkit.plugin.java.JavaPlugin;

public class KvantumPlugin extends JavaPlugin
{

    private SimpleKvantumBukkit simpleKvantumBukkit;

    @Override
    public void onLoad()
    {
        this.simpleKvantumBukkit = new SimpleKvantumBukkit( getDataFolder() );
    }

    @Override
    public void onEnable()
    {
        this.getLogger().info( "Starting Kvantum..." );
        this.simpleKvantumBukkit.start();
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info( "Stopping kvantum" );
        this.simpleKvantumBukkit.stop();
    }

}
