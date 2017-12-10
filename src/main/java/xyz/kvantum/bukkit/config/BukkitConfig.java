package xyz.kvantum.bukkit.config;

import com.intellectualsites.configurable.ConfigurationImplementation;
import com.intellectualsites.configurable.annotations.ConfigSection;
import com.intellectualsites.configurable.annotations.Configuration;

/**
 * KvantumBukkit configuration file
 */
@Configuration( implementation = ConfigurationImplementation.YAML )
public final class BukkitConfig
{

    private BukkitConfig()
    {
    }

    @ConfigSection
    public static class SimplePage
    {
        public static boolean enableDefaultView = true;
        public static String title = "KvantumBukkit";
    }

}
