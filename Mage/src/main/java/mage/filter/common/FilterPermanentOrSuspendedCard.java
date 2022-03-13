/*
 *
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 *
 */
package mage.filter.common;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class FilterPermanentOrSuspendedCard extends FilterImpl<MageObject> implements FilterInPlay<MageObject> {

    protected FilterCard cardFilter;
    protected FilterPermanent permanentFilter;

    public FilterPermanentOrSuspendedCard() {
        this("permanent or suspended card");
    }

    public FilterPermanentOrSuspendedCard(String name) {
        super(name);
        permanentFilter = new FilterPermanent();
        cardFilter = new FilterCard();
        cardFilter.add(new AbilityPredicate(SuspendAbility.class));
        cardFilter.add(CounterType.TIME.getPredicate());
    }

    public FilterPermanentOrSuspendedCard(final FilterPermanentOrSuspendedCard filter) {
        super(filter);
        this.permanentFilter = filter.permanentFilter.copy();
        this.cardFilter = filter.cardFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof MageObject;
    }

    @Override
    public boolean match(MageObject o, Game game) {
        if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, game);
        } else if (o instanceof Card) {
            return cardFilter.match((Card) o, game);
        }
        return false;
    }

    @Override
    public boolean match(MageObject o, UUID playerId, Ability source, Game game) {
        if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, playerId, source, game);
        } else if (o instanceof Card) {
            return cardFilter.match((Card) o, playerId, source, game);
        }
        return false;
    }

    public FilterPermanent getPermanentFilter() {
        return this.permanentFilter;
    }

    public FilterCard getCardFilter() {
        return this.cardFilter;
    }

    public void setPermanentFilter(FilterPermanent permanentFilter) {
        this.permanentFilter = permanentFilter;
    }

    public void setCardFilter(FilterCard cardFilter) {
        this.cardFilter = cardFilter;
    }

    @Override
    public FilterPermanentOrSuspendedCard copy() {
        return new FilterPermanentOrSuspendedCard(this);
    }
}
