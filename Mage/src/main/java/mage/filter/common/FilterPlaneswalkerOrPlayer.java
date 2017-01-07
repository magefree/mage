/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.filter.common;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import mage.filter.FilterImpl;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterPlaneswalkerOrPlayer extends FilterImpl<Object> {

    protected final FilterPlaneswalkerPermanent planeswalkerFilter;
    protected final FilterPlayer playerFilter;

    public FilterPlaneswalkerOrPlayer(Set<UUID> defenders) {
        super("planeswalker or player");

        ArrayList<Predicate<Permanent>> permanentPredicates = new ArrayList<>();
        for (UUID defenderId : defenders) {
            permanentPredicates.add(new ControllerIdPredicate(defenderId));
        }
        planeswalkerFilter = new FilterPlaneswalkerPermanent();
        planeswalkerFilter.add(Predicates.or(permanentPredicates));

        ArrayList<Predicate<Player>> playerPredicates = new ArrayList<>();
        for (UUID defenderId : defenders) {
            playerPredicates.add(new PlayerIdPredicate(defenderId));
        }
        playerFilter = new FilterPlayer();
        playerFilter.add(Predicates.or(playerPredicates));
    }

    public FilterPlaneswalkerOrPlayer(final FilterPlaneswalkerOrPlayer filter) {
        super(filter);
        this.planeswalkerFilter = filter.planeswalkerFilter.copy();
        this.playerFilter = filter.playerFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return true;
    }

    @Override
    public boolean match(Object o, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, game);
        } else if (o instanceof Permanent) {
            return planeswalkerFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public FilterPlaneswalkerOrPlayer copy() {
        return new FilterPlaneswalkerOrPlayer(this);
    }
}
