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
import java.util.logging.Logger;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetSpell;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author Rafbill
 */
public class MindbreakTrap extends CardImpl<MindbreakTrap> {

    public MindbreakTrap(UUID ownerId) {
        super(ownerId, 57, "Mindbreak Trap", Rarity.MYTHIC, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setBlue(true);

        // If an opponent cast three or more spells this turn, you may pay {0} rather than pay Mindbreak Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(
                new MindbreakTrapAlternativeCost());
        this.addWatcher(new MindbreakTrapWatcher());
        // Exile any number of target spells.
        this.getSpellAbility().addEffect(new MindbreakEffect());
    }

    public MindbreakTrap(final MindbreakTrap card) {
        super(card);
    }

    @Override
    public MindbreakTrap copy() {
        return new MindbreakTrap(this);
    }
}

class MindbreakTrapWatcher extends WatcherImpl<MindbreakTrapWatcher> {

    protected int spellCast;

    public MindbreakTrapWatcher() {
        super("opponent cast three or more spells");
        spellCast = 0;
    }

    public MindbreakTrapWatcher(final MindbreakTrapWatcher watcher) {
        super(watcher);
        this.spellCast = watcher.spellCast;
    }

    @Override
    public MindbreakTrapWatcher copy() {
        return new MindbreakTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.END_TURN_STEP_POST) {
            condition = false;
            spellCast = 0;
            return;
        }
        if (condition == true) // no need to check - condition has already occured
        {
            return;
        }
        Logger.getAnonymousLogger().info(((Integer) spellCast).toString());
        if (event.getType() == EventType.SPELL_CAST
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            spellCast++;
            if (spellCast >= 3) {
                condition = true;
            }
        }
    }
}

class MindbreakTrapAlternativeCost extends AlternativeCostImpl<MindbreakTrapAlternativeCost> {

    public MindbreakTrapAlternativeCost() {
        super("you may pay {0} rather than pay Mindbreak Trap's mana cost");
        this.add(new GenericManaCost(0));
    }

    public MindbreakTrapAlternativeCost(final MindbreakTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public MindbreakTrapAlternativeCost copy() {
        return new MindbreakTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get(source.getControllerId(), "opponent cast three or more spells");
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If a creature spell you cast this turn was countered by a spell or ability an opponent controlled, you may pay {0} rather than pay Mindbreak Trap's mana cost.";
    }
}
class MindbreakEffect extends OneShotEffect<MindbreakEffect>{

    MindbreakEffect(MindbreakEffect effect) {
        super(effect);
    }

    MindbreakEffect() {
        super(Outcome.Exile);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetSpell target = new TargetSpell(new FilterSpell("spell to exile"));
        while(game.getPlayer(source.getControllerId()).choose(Outcome.Exile, target, game)){
            game.getStack().getSpell(target.getFirstTarget()).moveToExile(null, null, source.getId(), game);
            target.clearChosen();
        }
        return true;
    }

    @Override
    public MindbreakEffect copy() {
        return new MindbreakEffect(this);
    }
    
}