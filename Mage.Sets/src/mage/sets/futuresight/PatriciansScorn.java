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
package mage.sets.futuresight;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.filter.FilterSpell;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class PatriciansScorn extends CardImpl<PatriciansScorn> { 

    public PatriciansScorn(UUID ownerId) {
        super(ownerId, 29, "Patrician's Scorn", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "FUT";

        this.color.setWhite(true);

        // If you've cast another white spell this turn, you may cast Patrician's Scorn without paying its mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new CastWhiteSpellThisTurnCondition()));
        this.addWatcher(new PatriciansScornWatcher());
        // Destroy all enchantments.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent()));
    }

    public PatriciansScorn(final PatriciansScorn card) {
        super(card);
    }

    @Override
    public PatriciansScorn copy() {
        return new PatriciansScorn(this);
    }
}


class CastWhiteSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        PatriciansScornWatcher watcher = (PatriciansScornWatcher) game.getState().getWatchers().get("PatriciansScornWatcher", source.getSourceId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }

    @Override
    public String toString() {
        return "If you've cast another white spell this turn";
    }
}

class PatriciansScornWatcher extends WatcherImpl<PatriciansScornWatcher> {

    private static final FilterSpell filter = new FilterSpell();
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PatriciansScornWatcher() {
        super("PatriciansScornWatcher", WatcherScope.CARD);
    }

    public PatriciansScornWatcher(final PatriciansScornWatcher watcher) {
        super(watcher);
    }

    @Override
    public PatriciansScornWatcher copy() {
        return new PatriciansScornWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == EventType.SPELL_CAST && controllerId.equals(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}
