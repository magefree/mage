/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.MyrToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MyrBattlesphere extends CardImpl<MyrBattlesphere> {

    public MyrBattlesphere(UUID ownerId) {
        super(ownerId, 180, "Myr Battlesphere", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Myr");
        this.subtype.add("Construct");
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MyrToken(), 4), false));
        this.addAbility(new MyrBattlesphereAbility());
    }

    public MyrBattlesphere(final MyrBattlesphere card) {
        super(card);
    }

    @Override
    public MyrBattlesphere copy() {
        return new MyrBattlesphere(this);
    }

}

class MyrBattlesphereAbility extends TriggeredAbilityImpl<MyrBattlesphereAbility> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Myr");

    static {
        filter.add(new SubtypePredicate("Myr"));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MyrBattlesphereAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(new GetXValue(), new StaticValue(0), Duration.EndOfTurn), true);
        this.addEffect(new MyrBattlesphereEffect());
        this.addCost(new TapVariableTargetCost(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, false)));
    }

    public MyrBattlesphereAbility(final MyrBattlesphereAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (costs.isPaid())
            return true;
        if (costs.canPay(this.getId(), this.getControllerId(), game))
            return costs.pay(this, game, this.getId(), this.getControllerId(), false);
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId()) ) {
            costs.clearPaid();
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} attacks, " + super.getRule();
    }

    @Override
    public MyrBattlesphereAbility copy() {
        return new MyrBattlesphereAbility(this);
    }

}

class MyrBattlesphereEffect extends OneShotEffect<MyrBattlesphereEffect> {

    private GetXValue amount = new GetXValue();

    public MyrBattlesphereEffect() {
        super(Outcome.Damage);
        staticText = "{source} deals X damage to defending player";
    }

    public MyrBattlesphereEffect(final MyrBattlesphereEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefendingPlayer(source.getSourceId());
        Player defender = game.getPlayer(defenderId);
        if (defender != null) {
            defender.damage(amount.calculate(game, source), source.getSourceId(), game, false, false);
            return true;
        }
        return false;
    }

    @Override
    public MyrBattlesphereEffect copy() {
        return new MyrBattlesphereEffect(this);
    }

}