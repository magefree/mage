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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class InfernoTrap extends CardImpl<InfernoTrap> {

    public InfernoTrap(UUID ownerId) {
        super(ownerId, 133, "Inferno Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setRed(true);

        // If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new InfernoTrapAlternativeCost());
        this.addWatcher(new ControllerDamagedByCreatureWatcher());
        
        // Inferno Trap deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public InfernoTrap(final InfernoTrap card) {
        super(card);
    }

    @Override
    public InfernoTrap copy() {
        return new InfernoTrap(this);
    }
}

class ControllerDamagedByCreatureWatcher extends WatcherImpl<ControllerDamagedByCreatureWatcher> {

    int numCreaturesDamagedController;

    public ControllerDamagedByCreatureWatcher() {
        super("ControllerDamagedByCreatureWatcher", Constants.WatcherScope.GAME);
    }

    public ControllerDamagedByCreatureWatcher(final ControllerDamagedByCreatureWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && event.getTargetId().equals(controllerId)) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                numCreaturesDamagedController += 1;
                if (numCreaturesDamagedController >= 2) {
                    condition = true;
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        numCreaturesDamagedController = 0;
    }

    @Override
    public ControllerDamagedByCreatureWatcher copy() {
        return new ControllerDamagedByCreatureWatcher(this);
    }
}

class InfernoTrapAlternativeCost extends AlternativeCostImpl<InfernoTrapAlternativeCost> {

    public InfernoTrapAlternativeCost() {
        super("you may pay {R} rather than pay Inferno Trap's mana cost");
        this.add(new ManaCostsImpl("{R}"));
    }

    public InfernoTrapAlternativeCost(final InfernoTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public InfernoTrapAlternativeCost copy() {
        return new InfernoTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        ControllerDamagedByCreatureWatcher watcher = (ControllerDamagedByCreatureWatcher) game.getState().getWatchers().get("ControllerDamagedByCreatureWatcher");
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost";
    }
}
