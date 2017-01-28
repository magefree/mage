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
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class LightningRunner extends CardImpl {

    public LightningRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Lightning Runner attacks, you get {E}{E}, then you may pay {E}{E}{E}{E}{E}{E}{E}{E}. If you do,
        // untap all creatures you control and after this phase, there is an additional combat phase.
        this.addAbility(new AttacksTriggeredAbility(new LightningRunnerEffect(), false));
    }

    public LightningRunner(final LightningRunner card) {
        super(card);
    }

    @Override
    public LightningRunner copy() {
        return new LightningRunner(this);
    }
}

class LightningRunnerEffect extends OneShotEffect {

    LightningRunnerEffect() {
        super(Outcome.Benefit);
        staticText = "you get {E}{E}, then you may pay {E}{E}{E}{E}{E}{E}{E}{E}. If you do, "
                + "untap all creatures you control and after this phase, there is an additional combat phase";
    }

    LightningRunnerEffect(final LightningRunnerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new GetEnergyCountersControllerEffect(2).apply(game, source);
            if (controller.getCounters().getCount(CounterType.ENERGY) > 7) {
                Cost cost = new PayEnergyCost(8);
                if (controller.chooseUse(outcome,
                        "Pay {E}{E}{E}{E}{E}{E}{E}{E} to use this? ",
                        "Untap all creatures you control and after this phase, there is an additional combat phase.",
                        "Yes", "No", source, game)
                        && cost.pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
                    new UntapAllControllerEffect(new FilterControlledCreaturePermanent()).apply(game, source);
                    new AdditionalCombatPhaseEffect().apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LightningRunnerEffect copy() {
        return new LightningRunnerEffect(this);
    }

}
