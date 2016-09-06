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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public class ChaliceOfTheVoid extends CardImpl {

    public ChaliceOfTheVoid(UUID ownerId) {
        super(ownerId, 150, "Chalice of the Void", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{X}{X}");
        this.expansionSetCode = "MRD";

        // Chalice of the Void enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.
        this.addAbility(new ChaliceOfTheVoidTriggeredAbility());
    }

    public ChaliceOfTheVoid(final ChaliceOfTheVoid card) {
        super(card);
    }

    @java.lang.Override
    public ChaliceOfTheVoid copy() {
        return new ChaliceOfTheVoid(this);
    }
}

class ChaliceOfTheVoidTriggeredAbility extends TriggeredAbilityImpl {

    public ChaliceOfTheVoidTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterEffect());
    }

    public ChaliceOfTheVoidTriggeredAbility(final ChaliceOfTheVoidTriggeredAbility abiltity) {
        super(abiltity);
    }

    @java.lang.Override
    public ChaliceOfTheVoidTriggeredAbility copy() {
        return new ChaliceOfTheVoidTriggeredAbility(this);
    }

    @java.lang.Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @java.lang.Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent chalice = game.getPermanent(getSourceId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && chalice != null && spell.getConvertedManaCost() == chalice.getCounters(game).getCount(CounterType.CHARGE)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @java.lang.Override
    public String getRule() {
        return "Whenever a player casts a spell with converted mana cost equal to the number of charge counters on {this}, counter that spell.";
    }
}

class CounterEffect extends OneShotEffect {

    public CounterEffect() {
        super(Outcome.Detriment);
    }

    public CounterEffect(final CounterEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public CounterEffect copy() {
        return new CounterEffect(this);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        return game.getStack().counter(this.getTargetPointer().getFirst(game, source), source.getSourceId(), game);
    }

}
