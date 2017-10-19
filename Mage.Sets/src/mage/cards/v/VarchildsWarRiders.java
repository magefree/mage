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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.RampageAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterOpponent;
import mage.game.Game;
import mage.game.permanent.token.SurvivorToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class VarchildsWarRiders extends CardImpl {

    public VarchildsWarRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Cumulative upkeep-Put a 1/1 red Survivor creature token onto the battlefield under an opponent's control.
        this.addAbility(new CumulativeUpkeepAbility(new OpponentCreateSurvivorTokenCost()));

        // Trample; rampage 1
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new RampageAbility(1));
    }

    public VarchildsWarRiders(final VarchildsWarRiders card) {
        super(card);
    }

    @Override
    public VarchildsWarRiders copy() {
        return new VarchildsWarRiders(this);
    }
}

class OpponentCreateSurvivorTokenCost extends CostImpl {

    private static final FilterOpponent filter = new FilterOpponent();

    public OpponentCreateSurvivorTokenCost() {
        this.text = "have an opponent create a 1/1 red Survivor creature token";
    }

    public OpponentCreateSurvivorTokenCost(OpponentCreateSurvivorTokenCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (!game.getOpponents(controllerId).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetPlayer target = new TargetPlayer(1, 1, true, filter);
            if (controller.chooseTarget(Outcome.Detriment, target, ability, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    Effect effect = new CreateTokenTargetEffect(new SurvivorToken());
                    effect.setTargetPointer(new FixedTarget(opponent.getId(), game));
                    paid = effect.apply(game, ability);
                }
            }
        }
        return paid;
    }

    @Override
    public OpponentCreateSurvivorTokenCost copy() {
        return new OpponentCreateSurvivorTokenCost(this);
    }

}
