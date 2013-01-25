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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.UnblockableSourceEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class IncursionSpecialist extends CardImpl<IncursionSpecialist> {

    public IncursionSpecialist(UUID ownerId) {
        super(ownerId, 38, "Incursion Specialist", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast your second spell each turn, Incursion Specialist gets +2/+0 until end of turn and is unblockable this turn.
        this.addAbility(new IncursionTriggeredAbility());
        this.addWatcher(new IncursionWatcher());
    }

    public IncursionSpecialist(final IncursionSpecialist card) {
        super(card);
    }

    @Override
    public IncursionSpecialist copy() {
        return new IncursionSpecialist(this);
    }
}

class IncursionTriggeredAbility extends TriggeredAbilityImpl<IncursionTriggeredAbility> {

    public IncursionTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Constants.Duration.EndOfTurn));
        this.addEffect(new UnblockableSourceEffect(Constants.Duration.EndOfTurn));
    }

    public IncursionTriggeredAbility(final IncursionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IncursionTriggeredAbility copy() {
        return new IncursionTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(controllerId)) {
            Watcher watcher = game.getState().getWatchers().get("SecondSpellCast", controllerId);
            if (watcher != null && watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your second spell each turn, Incursion Specialist gets +2/+0 until end of turn and is unblockable this turn.";
    }
}

class IncursionWatcher extends WatcherImpl<IncursionWatcher> {

    int spellCount = 0;

    public IncursionWatcher() {
        super("SecondSpellCast", Constants.WatcherScope.PLAYER);
    }

    public IncursionWatcher(final IncursionWatcher watcher) {
        super(watcher);
        this.spellCount = watcher.spellCount;
    }

    @Override
    public IncursionWatcher copy() {
        return new IncursionWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        condition = false;
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(controllerId)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                spellCount++;
                if (spellCount == 2) {
                    condition = true;
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellCount = 0;
    }
}