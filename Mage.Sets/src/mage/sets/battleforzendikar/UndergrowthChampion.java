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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
 * @author fireshoes
 */
public class UndergrowthChampion extends CardImpl {

    public UndergrowthChampion(UUID ownerId) {
        super(ownerId, 197, "Undergrowth Champion", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Elemental");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If damage would be dealt to Undergrowth Champion while it has a +1/+1 counter on it, prevent that damage and remove a +1/+1 counter from Undergrowth Champion.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UndergrowthChampionPreventionEffect()));

        // <i>Landfall</i>-Whenever a land enters the battlefield under your control, put a +1/+1 counter on Undergrowth Champion.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    public UndergrowthChampion(final UndergrowthChampion card) {
        super(card);
    }

    @Override
    public UndergrowthChampion copy() {
        return new UndergrowthChampion(this);
    }
}

class UndergrowthChampionPreventionEffect extends PreventionEffectImpl {

    // remember turn and phase step to check if counter in this step was already removed
    private int turn = 0;
    private Step combatPhaseStep = null;

    public UndergrowthChampionPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If damage would be dealt to {this} while it has a +1/+1 counter on it, prevent that damage and remove a +1/+1 counter from {this}";
    }

    public UndergrowthChampionPreventionEffect(final UndergrowthChampionPreventionEffect effect) {
        super(effect);
        this.turn = effect.turn;
        this.combatPhaseStep = effect.combatPhaseStep;
    }

    @Override
    public UndergrowthChampionPreventionEffect copy() {
        return new UndergrowthChampionPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
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
                preventDamageAction(event, source, game);
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
