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
    public static final class API
    {

        public static boolean enableGetters = true;

    }

}
