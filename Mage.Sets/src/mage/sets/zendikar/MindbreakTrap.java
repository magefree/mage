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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author Rafbill
 */
public class MindbreakTrap extends CardImpl<MindbreakTrap> {

    private static final FilterSpell filter = new FilterSpell("spell to exile");

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
        this.getSpellAbility().addTarget(new TargetSpell(0, Integer.MAX_VALUE, filter));
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

    private Map<UUID, Integer> counts = new HashMap<UUID, Integer>();

    public MindbreakTrapWatcher() {
        super("opponent cast three or more spells", WatcherScope.PLAYER);
    }

    public MindbreakTrapWatcher(final MindbreakTrapWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry: watcher.counts.entrySet()) {
            counts.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public MindbreakTrapWatcher copy() {
        return new MindbreakTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { // no need to check - condition has already occured
            return;
        }
        if (event.getType() == EventType.SPELL_CAST
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            int count = 1;
            if (counts.containsKey(event.getPlayerId())) {
                count += counts.get(event.getPlayerId());
                if (count >= 3)
                    condition = true;
            }
            counts.put(event.getPlayerId(), count);
        }
    }

    @Override
    public void reset() {
        super.reset();
        counts.clear();
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
        Watcher watcher = game.getState().getWatchers().get("opponent cast three or more spells", source.getControllerId());
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent cast three or more spells this turn, you may pay {0} rather than pay Mindbreak Trap's mana cost.";
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
        int affectedTargets = 0;
        if (targetPointer.getTargets(game, source).size() > 0) {
            for (UUID spellId : targetPointer.getTargets(game, source)) {
                Spell spell = game.getStack().getSpell(spellId);
                if (spell != null) {
                    spell.moveToExile(null, null, source.getId(), game);
                    affectedTargets++;
                }
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public MindbreakEffect copy() {
        return new MindbreakEffect(this);
    }

}