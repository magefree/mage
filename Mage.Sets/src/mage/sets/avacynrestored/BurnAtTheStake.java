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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author North
 */
public class BurnAtTheStake extends CardImpl<BurnAtTheStake> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public BurnAtTheStake(UUID ownerId) {
        super(ownerId, 130, "Burn at the Stake", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}{R}{R}");
        this.expansionSetCode = "AVR";

        this.color.setRed(true);

        // As an additional cost to cast Burn at the Stake, tap any number of untapped creatures you control.
        this.getSpellAbility().addCost(new TapVariableTargetCost(filter, true, "any number of"));
        // Burn at the Stake deals damage to target creature or player equal to three times the number of creatures tapped this way.
        this.getSpellAbility().addEffect(new BurnAtTheStakeEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer(true));
    }

    public BurnAtTheStake(final BurnAtTheStake card) {
        super(card);
    }

    @Override
    public BurnAtTheStake copy() {
        return new BurnAtTheStake(this);
    }
}

class BurnAtTheStakeEffect extends OneShotEffect<BurnAtTheStakeEffect> {

    public BurnAtTheStakeEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to target creature or player equal to three times the number of creatures tapped this way";
    }

    public BurnAtTheStakeEffect(final BurnAtTheStakeEffect effect) {
        super(effect);
    }

    @Override
    public BurnAtTheStakeEffect copy() {
        return new BurnAtTheStakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (new GetXValue()).calculate(game, source) * 3;

        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            permanent.damage(amount, source.getSourceId(), game, true, false);
            return true;
        }

        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.damage(amount, source.getSourceId(), game, false, true);
            return true;
        }

        return false;
    }
}
