package xyz.kvantum.bukkit.views;

import xyz.kvantum.bukkit.config.BukkitConfig;
import xyz.kvantum.bukkit.util.PlayerUtil;
import xyz.kvantum.server.api.request.AbstractRequest;
import xyz.kvantum.server.api.request.HttpMethod;
import xyz.kvantum.server.api.views.staticviews.ViewMatcher;
import xyz.kvantum.server.api.views.staticviews.converters.StandardConverters;

import static j2html.TagCreator.*;

@SuppressWarnings( "unused" )
public class SimpleWebPage
{

    @ViewMatcher( filter = "", httpMethod = HttpMethod.ALL, outputType = StandardConverters.HTML )
    public String index(final AbstractRequest request)
    {
        return document( html(
                head(
                        title( BukkitConfig.SimplePage.title ),
                        link().withRel( "stylesheet" ).withHref( "https://maxcdn.bootstrapcdn" +
                                ".com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" ),
                        meta().withName( "viewport" ).withContent( "width=device-width, initial-scale=1, " +
                                "shrink-to-fit=no" ),
                        meta().withCharset( "utf-8" )
                ),
                body(
                        h1().withText( "Hello {{session.id}}" ),
                        h2().withText( "Online players: " ),
                        each( PlayerUtil.getOnlinePlayers(), player ->
                                span().withText( player.getUsername() ) ),
                        script().withSrc( "https://code.jquery.com/jquery-3.2.1.slim.min.js" ),
                        script().withSrc( "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" ),
                        script().withSrc( "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" )
                )
        ).withLang( "en" ) );
    }

}
