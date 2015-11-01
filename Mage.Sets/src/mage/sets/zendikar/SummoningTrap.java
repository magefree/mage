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
package mage.sets.zendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.Watcher;

/**
 *
 * @author Rafbill
 */
public class SummoningTrap extends CardImpl {

    public SummoningTrap(UUID ownerId) {
        super(ownerId, 184, "Summoning Trap", Rarity.RARE,
                new CardType[]{CardType.INSTANT}, "{4}{G}{G}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        // If a creature spell you cast this turn was countered by a spell or
        // ability an opponent controlled, you may pay {0} rather than pay
        // Summoning Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(
                new SummoningTrapAlternativeCost());
        this.getSpellAbility().addWatcher(new SummoningTrapWatcher());
        // Look at the top seven cards of your library. You may put a creature
        // card from among them onto the battlefield. Put the rest on the bottom
        // of your library in any order.
        this.getSpellAbility().addEffect(new SummoningTrapEffect());
    }

    public SummoningTrap(final SummoningTrap card) {
        super(card);
    }

    @Override
    public SummoningTrap copy() {
        return new SummoningTrap(this);
    }
}

class SummoningTrapWatcher extends Watcher {

    public SummoningTrapWatcher() {
        super("CreatureSpellCountered", WatcherScope.PLAYER);
    }

    public SummoningTrapWatcher(final SummoningTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public SummoningTrapWatcher copy() {
        return new SummoningTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) {// no need to check - condition has already occured
            return;
        }
        if (event.getType() == EventType.COUNTERED) {
            StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
            if (stackObject == null) {
                stackObject = (StackObject) game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
            }
            StackObject counterObject = game.getStack().getStackObject(event.getSourceId());
            if (counterObject == null) {
                counterObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
            }
            if (stackObject != null && counterObject != null
                    && stackObject.getCardType().contains(CardType.CREATURE)
                    && game.getOpponents(controllerId).contains(counterObject.getControllerId())) {
                condition = true;
            }
        }
    }
}

class SummoningTrapAlternativeCost extends AlternativeCostImpl<Cost> {

    public SummoningTrapAlternativeCost() {
        super("you may pay {0} rather than pay Summoning Trap's mana cost");
        this.add(new GenericManaCost(0));
    }

    public SummoningTrapAlternativeCost(final SummoningTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public SummoningTrapAlternativeCost copy() {
        return new SummoningTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get("CreatureSpellCountered", source.getControllerId());
        return watcher != null && watcher.conditionMet();
    }

    @Override
    public String getText() {
        return "If a creature spell you cast this turn was countered by a spell or ability an opponent controlled, you may pay {0} rather than pay Summoning Trap's mana cost";
    }
}

class SummoningTrapEffect extends OneShotEffect {

    public SummoningTrapEffect(final SummoningTrapEffect effect) {
        super(effect);
    }

    public SummoningTrapEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Look at the top seven cards of your library. You may put a creature card from among them onto the battlefield. Put the rest on the bottom of your library in any order";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(Zone.LIBRARY);
        cards.addAll(controller.getLibrary().getTopCards(game, 7));
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(Zone.LIBRARY,
                    new FilterCreatureCard(
                            "creature card to put on the battlefield"));
            if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            if (cards.size() > 0) {
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            }
        }
        return true;
    }

    @Override
    public SummoningTrapEffect copy() {
        return new SummoningTrapEffect(this);
    }
}
