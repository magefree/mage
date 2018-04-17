/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import java.util.UUID;
import mage.MageItem;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class FilterPlayerOrPlaneswalker extends FilterImpl<MageItem> implements FilterInPlay<MageItem> {

    protected FilterPlaneswalkerPermanent planeswalkerFilter;
    protected final FilterPlayer playerFilter;

    public FilterPlayerOrPlaneswalker() {
        this("player or planeswalker");
    }

    public FilterPlayerOrPlaneswalker(String name) {
        super(name);
        planeswalkerFilter = new FilterPlaneswalkerPermanent();
        playerFilter = new FilterPlayer();
    }

    public FilterPlayerOrPlaneswalker(final FilterPlayerOrPlaneswalker filter) {
        super(filter);
        this.planeswalkerFilter = filter.planeswalkerFilter.copy();
        this.playerFilter = filter.playerFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return true;
    }

    @Override
    public boolean match(MageItem o, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, game);
        } else if (o instanceof Permanent) {
            return planeswalkerFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public boolean match(MageItem o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, sourceId, playerId, game);
        } else if (o instanceof Permanent) {
            return planeswalkerFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return false;
    }

    public FilterPlaneswalkerPermanent getPlaneswalkerFilter() {
        return this.planeswalkerFilter;
    }

    public FilterPlayer getPlayerFilter() {
        return this.playerFilter;
    }

    public void setPlaneswalkerFilter(FilterPlaneswalkerPermanent planeswalkerFilter) {
        this.planeswalkerFilter = planeswalkerFilter;
    }

    @Override
    public FilterPlayerOrPlaneswalker copy() {
        return new FilterPlayerOrPlaneswalker(this);
    }

}
