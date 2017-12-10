package xyz.kvantum.bukkit.views.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import xyz.kvantum.bukkit.objects.KvantumPlayer;
import xyz.kvantum.bukkit.util.PlayerUtil;
import xyz.kvantum.server.api.request.AbstractRequest;
import xyz.kvantum.server.api.request.HttpMethod;
import xyz.kvantum.server.api.views.staticviews.ViewMatcher;
import xyz.kvantum.server.api.views.staticviews.converters.StandardConverters;

import java.util.stream.Collectors;

@SuppressWarnings( { "WeakerAccess", "unused" } )
public final class Getters
{

    @ViewMatcher( filter = "api/players/online", httpMethod = HttpMethod.GET, outputType = StandardConverters.JSON )
    public JSONObject getOnlinePlayers(final AbstractRequest request)
    {
        final JSONObject object = new JSONObject();
        final JSONArray array = new JSONArray( PlayerUtil.getOnlinePlayers().stream()
                .map( KvantumPlayer::toJson ).collect( Collectors.toList() ) );
        object.put( "players", array );
        return object;
    }

}
