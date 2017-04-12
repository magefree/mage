/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
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
 */
package mage.cards.h;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public class HibernationsEnd extends CardImpl {

    public HibernationsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));
        // Whenever you pay Hibernation's End's cumulative upkeep, you may search your library for a creature card with converted mana cost equal to the number of age counters on Hibernation's End and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new HibernationsEndAbility());
    }

    public HibernationsEnd(final HibernationsEnd card) {
        super(card);
    }

    @Override
    public HibernationsEnd copy() {
        return new HibernationsEnd(this);
    }
}

class HibernationsEndAbility extends TriggeredAbilityImpl {

    public HibernationsEndAbility() {
        super(Zone.BATTLEFIELD, new HibernationsEndEffect(), true);
    }

    public HibernationsEndAbility(final HibernationsEndAbility ability) {
        super(ability);
    }

    @Override
    public HibernationsEndAbility copy() {
        return new HibernationsEndAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PAID_CUMULATIVE_UPKEEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId() != null && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever you pay {this}'s cumulative upkeep, " + super.getRule();
    }
}

class HibernationsEndEffect extends OneShotEffect {

    public HibernationsEndEffect() {
        super(Outcome.Benefit);
        this.staticText = "search your library for a creature card with converted mana cost equal to the number of age counters on {this} and put it onto the battlefield. If you do, shuffle your library.";
    }

    public HibernationsEndEffect(final HibernationsEndEffect effect) {
        super(effect);
    }

    @Override
    public HibernationsEndEffect copy() {
        return new HibernationsEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && player != null) {
            int newConvertedCost = sourcePermanent.getCounters(game).getCount("age");
            FilterCard filter = new FilterCard("creature card with converted mana cost " + newConvertedCost);
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, newConvertedCost));
            filter.add(new CardTypePredicate(CardType.CREATURE));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            return new SearchLibraryPutInPlayEffect(target).apply(game, source);
        }
        return false;
    }
}
