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
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class RuneflareTrap extends CardImpl<RuneflareTrap> {

    public RuneflareTrap(UUID ownerId) {
        super(ownerId, 146, "Runeflare Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{R}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setRed(true);

        // If an opponent drew three or more cards this turn, you may pay {R} rather than pay Runeflare Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new RuneflareTrapAlternativeCost());
        this.addWatcher(new CardsDrawnOpponentWatcher());

        // Runeflare Trap deals damage to target player equal to the number of cards in that player's hand.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new TargetPlayerCardsInHandCount()));
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public RuneflareTrap(final RuneflareTrap card) {
        super(card);
    }

    @Override
    public RuneflareTrap copy() {
        return new RuneflareTrap(this);
    }
}

class TargetPlayerCardsInHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Player targetPlayer = game.getPlayer(sourceAbility.getFirstTarget());
        if (targetPlayer != null) {
            return targetPlayer.getHand().size();
        }

        return 0;
    }

    @Override
    public DynamicValue clone() {
        return new TargetPlayerCardsInHandCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "target player's cards in hand";
    }
}

class CardsDrawnOpponentWatcher extends WatcherImpl<CardsDrawnOpponentWatcher> {

    int cardsDrawn;

    public CardsDrawnOpponentWatcher() {
        super("CardsDrawnOpponentWatcher", WatcherScope.GAME);
    }

    public CardsDrawnOpponentWatcher(final CardsDrawnOpponentWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            cardsDrawn += 1;
            if (cardsDrawn >= 3) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        cardsDrawn = 0;
    }

    @Override
    public CardsDrawnOpponentWatcher copy() {
        return new CardsDrawnOpponentWatcher(this);
    }
}

class RuneflareTrapAlternativeCost extends AlternativeCostImpl<RuneflareTrapAlternativeCost> {

    public RuneflareTrapAlternativeCost() {
        super("you may pay {R} rather than pay Runeflare Trap's mana cost");
        this.add(new ManaCostsImpl("{R}"));
    }

    public RuneflareTrapAlternativeCost(final RuneflareTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public RuneflareTrapAlternativeCost copy() {
        return new RuneflareTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        CardsDrawnOpponentWatcher watcher = (CardsDrawnOpponentWatcher) game.getState().getWatchers().get("CardsDrawnOpponentWatcher");
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent drew three or more cards this turn, you may pay {R} rather than pay Runeflare Trap's mana cost";
    }
}
