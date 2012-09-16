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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public class NightDealings extends CardImpl<NightDealings> {

    public NightDealings (UUID ownerId) {
        super(ownerId, 132, "Night Dealings", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "CHK";
        this.color.setBlack(true);
        // Whenever a source you control deals damage to another player, put that many theft counters on Night Dealings.
        this.addAbility((new NightDealingsTriggeredAbility()));

        // {2}{B}{B}, Remove X theft counters from Night Dealings: Search your library for a nonland card with converted mana cost X, reveal it, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NightDealingsSearchEffect(), new ManaCostsImpl("{2}{B}{B}"));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.THEFT.createInstance(1)));
        this.addAbility(ability);
    }

    public NightDealings (final NightDealings card) {
        super(card);
    }

    @Override
    public NightDealings copy() {
        return new NightDealings(this);
    }


    private class NightDealingsTriggeredAbility extends TriggeredAbilityImpl<NightDealingsTriggeredAbility> {

        public NightDealingsTriggeredAbility() {
            super(Constants.Zone.BATTLEFIELD, new NightDealingsEffect());
        }

        public NightDealingsTriggeredAbility(final NightDealingsTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public NightDealingsTriggeredAbility copy() {
            return new NightDealingsTriggeredAbility(this);
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
                // to another player    
                if (this.getControllerId() != event.getTargetId()) {
                    // a source you control
                    UUID sourceControllerId = null;
                    Permanent permanent = game.getPermanent(event.getSourceId());
                    if (permanent != null) {
                        sourceControllerId = permanent.getControllerId();
                    } else {
                        StackObject sourceStackObject = game.getStack().getStackObject(event.getSourceId());
                        if (sourceStackObject != null) {
                            sourceControllerId = sourceStackObject.getControllerId();
                        }
                    }
                    if (sourceControllerId != null && sourceControllerId == this.getControllerId()) {
                        // save amount of damage to effect
                        this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a source you control deals damage to another player, " + super.getRule();
        }
    }

    private class NightDealingsEffect extends OneShotEffect<NightDealingsEffect> {

        public NightDealingsEffect() {
            super(Constants.Outcome.Damage);
            this.staticText = "put that many theft counters on Night Dealings";
        }

        public NightDealingsEffect(final NightDealingsEffect effect) {
            super(effect);
        }

        @Override
        public NightDealingsEffect copy() {
            return new NightDealingsEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Integer damageAmount = (Integer) this.getValue("damageAmount");
            if (damageAmount != null) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.addCounters(CounterType.THEFT.createInstance(damageAmount), game);
                    return true;
                }
            }
            return false;
        }
    }

    private class NightDealingsSearchEffect extends OneShotEffect<NightDealingsSearchEffect> {

        public NightDealingsSearchEffect() {
            super(Constants.Outcome.DrawCard);
            this.staticText = "Search your library for a nonland card with converted mana cost X, reveal it, and put it into your hand. Then shuffle your library";
        }

        public NightDealingsSearchEffect(final NightDealingsSearchEffect effect) {
            super(effect);
        }

        @Override
        public NightDealingsSearchEffect copy() {
            return new NightDealingsSearchEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }

            int cmc = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    cmc = ((RemoveVariableCountersSourceCost)cost).getAmount();
                }
            } 

            FilterNonlandCard filter = new FilterNonlandCard("nonland card with converted mana cost X = " + cmc);
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, cmc));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);

            if (player.searchLibrary(target, game)) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getId(), game, false);    
                    
                    String name = "Reveal";
                    Cards cards = new CardsImpl();
                    cards.add(card);
                    Card sourceCard = game.getCard(source.getSourceId());
                    if (sourceCard != null) {
                        name = sourceCard.getName();
                    }
                    player.revealCards(name, cards, game);
                }
                player.shuffleLibrary(game);
                return true;
            }
            player.shuffleLibrary(game);
            return false;
        }
    }

}