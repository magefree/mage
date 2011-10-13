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
import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.SnakeToken;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author Rafbill
 */
public class CobraTrap extends CardImpl<CobraTrap> {

    public CobraTrap(UUID ownerId) {
        super(ownerId, 160, "Cobra Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{G}{G}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setGreen(true);

        // If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled, you may pay {G} rather than pay Cobra Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(
                new CobraTrapAlternativeCost());
        this.addWatcher(new CobraTrapWatcher());
        // Put four 1/1 green Snake creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken(), 4));
    }

    public CobraTrap(final CobraTrap card) {
        super(card);
    }

    @Override
    public CobraTrap copy() {
        return new CobraTrap(this);
    }
}

class CobraTrapWatcher extends WatcherImpl<CobraTrapWatcher> {

    public CobraTrapWatcher() {
        super("noncreature permanent destroyed", WatcherScope.PLAYER);
    }

    public CobraTrapWatcher(final CobraTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public CobraTrapWatcher copy() {
        return new CobraTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { // no need to check - condition has already occured
            return;
        }
        if (event.getType() == EventType.DESTROYED_PERMANENT
                && !game.getPlayer(controllerId).getGraveyard().get(event.getTargetId(), game).getCardType().contains(CardType.CREATURE)
                && game.getStack().getStackObject(event.getSourceId()) != null
                && game.getOpponents(controllerId).contains(game.getStack().getStackObject(event.getSourceId()).getControllerId())) {
            condition = true;
        }
    }
}

class CobraTrapAlternativeCost extends AlternativeCostImpl<CobraTrapAlternativeCost> {

    public CobraTrapAlternativeCost() {
        super("you may pay {G} rather than pay Cobra Trap's mana cost");
        this.add(new ColoredManaCost(ColoredManaSymbol.G));
    }

    public CobraTrapAlternativeCost(final CobraTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public CobraTrapAlternativeCost copy() {
        return new CobraTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get("noncreature permanent destroyed", source.getControllerId());
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled, you may pay {G} rather than pay Cobra Trap's mana cost.";
    }
}
