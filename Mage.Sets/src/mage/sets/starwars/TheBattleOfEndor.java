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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EwokToken;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class TheBattleOfEndor extends CardImpl {

    public TheBattleOfEndor(UUID ownerId) {
        super(ownerId, 130, "The Battle of Endor", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{X}{X}{G}{G}{G}");
        this.expansionSetCode = "SWS";

        // Create X 1/1 green Ewok creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EwokToken(), new ManacostVariableValue()));

        // Put X +1/+1 counters on each creature you control.
        this.getSpellAbility().addEffect(new TheBattleOfEndorEffect());

        // Creatures you control gain trample and haste until end of turn.
        Effect effect = new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent());
        effect.setText("Creatures you control gain trample");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent());
        effect.setText("and haste until end of turn");
        this.getSpellAbility().addEffect(effect);

    }

    public TheBattleOfEndor(final TheBattleOfEndor card) {
        super(card);
    }

    @Override
    public TheBattleOfEndor copy() {
        return new TheBattleOfEndor(this);
    }
}

class TheBattleOfEndorEffect extends OneShotEffect {

    TheBattleOfEndorEffect() {
        super(Outcome.Benefit);
        staticText = "Put X +1/+1 counters on each creature you control";
    }

    TheBattleOfEndorEffect(TheBattleOfEndorEffect effect) {
        super(effect);
    }

    @Override
    public TheBattleOfEndorEffect copy() {
        return new TheBattleOfEndorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(source.getManaCostsToPay().getX()), game);
            }
            return true;
        }
        return false;
    }

}
