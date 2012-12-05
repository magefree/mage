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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.NumberOfTargetsPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class RicochetTrap extends CardImpl<RicochetTrap> {

    final static private FilterSpell filter = new FilterSpell();

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public RicochetTrap(UUID ownerId) {
        super(ownerId, 87, "Ricochet Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Trap");

        this.color.setRed(true);

        // If an opponent cast a blue spell this turn, you may pay {R} rather than pay Ricochet Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new RicochetTrapAlternativeCost());

        // Change the target of target spell with a single target.
        this.getSpellAbility().addEffect(new RicochetTrapEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        this.addWatcher(new RicochetTrapWatcher());
    }

    public RicochetTrap(final RicochetTrap card) {
        super(card);
    }

    @Override
    public RicochetTrap copy() {
        return new RicochetTrap(this);
    }
}

class RicochetTrapWatcher extends WatcherImpl<RicochetTrapWatcher> {

    public RicochetTrapWatcher() {
        super("RicochetTrapWatcher", WatcherScope.GAME);
    }

    public RicochetTrapWatcher(final RicochetTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public RicochetTrapWatcher copy() {
        return new RicochetTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) //no need to check - condition has already occured
        {
            return;
        }
        if (event.getType() == EventType.SPELL_CAST
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell.getColor().isBlue()) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        condition = false;
    }
}

class RicochetTrapAlternativeCost extends AlternativeCostImpl<RicochetTrapAlternativeCost> {

    public RicochetTrapAlternativeCost() {
        super("You may pay {R} rather than pay Ricochet Trap's mana cost");
        this.add(new ColoredManaCost(Constants.ColoredManaSymbol.R));
    }

    public RicochetTrapAlternativeCost(final RicochetTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public RicochetTrapAlternativeCost copy() {
        return new RicochetTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        RicochetTrapWatcher watcher = (RicochetTrapWatcher) game.getState().getWatchers().get("RicochetTrapWatcher");
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent cast a blue spell this turn, you may pay {R} rather than pay {this} mana cost";
    }
}

class RicochetTrapEffect extends OneShotEffect<RicochetTrapEffect> {

    public RicochetTrapEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "Change the target of target spell with a single target";
    }

    public RicochetTrapEffect(final RicochetTrapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null && source.getControllerId() != null) {
            return spell.chooseNewTargets(game, source.getControllerId());
        }
        return false;
    }

    @Override
    public RicochetTrapEffect copy() {
        return new RicochetTrapEffect(this);
    }
}