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
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class ImminentDoom extends CardImpl {

    public ImminentDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Imminent Doom enters the battlefield with a doom counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.DOOM.createInstance(1))));

        // Whenever you cast a spell with converted mana cost equal to the number of doom counters on Imminent Doom, Imminent Doom deals that much damage to target creature or player. Then put a doom counter on Imminent Doom.
        Ability ability = new ImminentDoomTriggeredAbility();
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

    }

    public ImminentDoom(final ImminentDoom card) {
        super(card);
    }

    @Override
    public ImminentDoom copy() {
        return new ImminentDoom(this);
    }
}

class ImminentDoomTriggeredAbility extends TriggeredAbilityImpl {

    private String rule = "Whenever you cast a spell with converted mana cost equal to the number of doom counters on {this}, {this} deals that much damage to target creature or player. Then put a doom counter on {this}.";

    public ImminentDoomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ImminentDoomEffect());
    }

    public ImminentDoomTriggeredAbility(final ImminentDoomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImminentDoomTriggeredAbility copy() {
        return new ImminentDoomTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent imminentDoom = game.getPermanent(getSourceId());
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && imminentDoom != null
                    && spell.getConvertedManaCost() == imminentDoom.getCounters(game).getCount(CounterType.DOOM)) {
                game.getState().setValue("ImminentDoomCount" + getSourceId().toString(), imminentDoom.getCounters(game).getCount(CounterType.DOOM)); // store its current value
                return true;
            }
            }
        return false;
    }

    @Override
    public String getRule() {
        return rule;
    }
}

class ImminentDoomEffect extends OneShotEffect {

    public ImminentDoomEffect() {
        super(Outcome.Detriment);
    }

    public ImminentDoomEffect(final ImminentDoomEffect effect) {
        super(effect);
    }

    @Override
    public ImminentDoomEffect copy() {
        return new ImminentDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent imminentDoom = game.getPermanent(source.getSourceId());
        if (imminentDoom != null) {
            Effect effect = new DamageTargetEffect((int) game.getState().getValue("ImminentDoomCount" + source.getSourceId().toString()));
            effect.apply(game, source);
            imminentDoom.addCounters(CounterType.DOOM.createInstance(), source, game);
            game.getState().setValue("ImminentDoomCount" + source.getSourceId().toString(), 0); // reset to 0 to avoid any silliness
            return true;
        }
        return false;
    }

}
