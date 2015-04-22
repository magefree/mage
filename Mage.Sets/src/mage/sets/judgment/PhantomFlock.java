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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;

/**
 *
 * @author emerald000
 */
public class PhantomFlock extends CardImpl {

    public PhantomFlock(UUID ownerId) {
        super(ownerId, 16, "Phantom Flock", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "JUD";
        this.subtype.add("Bird");
        this.subtype.add("Soldier");
        this.subtype.add("Spirit");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Phantom Flock enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));
        
        // If damage would be dealt to Phantom Flock, prevent that damage. Remove a +1/+1 counter from Phantom Flock.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhantomFlockPreventionEffect()));
    }

    public PhantomFlock(final PhantomFlock card) {
        super(card);
    }

    @Override
    public PhantomFlock copy() {
        return new PhantomFlock(this);
    }
}

class PhantomFlockPreventionEffect extends PreventionEffectImpl {

    // remember turn and phase step to check if counter in this step was already removed
    private int turn = 0;
    private Step combatPhaseStep = null;

    PhantomFlockPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If damage would be dealt to {this}, prevent that damage. Remove a +1/+1 counter from {this}";
    }

    PhantomFlockPreventionEffect(final PhantomFlockPreventionEffect effect) {
        super(effect);
        this.turn = effect.turn;
        this.combatPhaseStep = effect.combatPhaseStep;
    }

    @Override
    public PhantomFlockPreventionEffect copy() {
        return new PhantomFlockPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);

        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            boolean removeCounter = true;
            // check if in the same combat damage step already a counter was removed
            if (game.getTurn().getPhase().getStep().getType().equals(PhaseStep.COMBAT_DAMAGE)) {
                if (game.getTurnNum() == turn
                        && game.getTurn().getStep().equals(combatPhaseStep)) {
                    removeCounter = false;
                } else {
                    turn = game.getTurnNum();
                    combatPhaseStep = game.getTurn().getStep();
                }
            }

            if(removeCounter && permanent.getCounters().containsKey(CounterType.P1P1)) {
                StringBuilder sb = new StringBuilder(permanent.getName()).append(": ");
                permanent.removeCounters(CounterType.P1P1.createInstance(), game);
                sb.append("Removed a +1/+1 counter ");
                game.informPlayers(sb.toString());
            }
        }

        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}
