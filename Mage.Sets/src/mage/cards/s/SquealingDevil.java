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
package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author TheElk801
 */
public class SquealingDevil extends CardImpl {

    public SquealingDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add("Devil");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // When Squealing Devil enters the battlefield, you may pay {X}. If you do, target creature gets +X/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SquealingDevilEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // When Squealing Devil enters the battlefield, sacrifice it unless {B} was spent to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(new ManaWasSpentCondition(ColoredManaSymbol.B)), false), new ManaSpentToCastWatcher());

    }

    public SquealingDevil(final SquealingDevil card) {
        super(card);
    }

    @Override
    public SquealingDevil copy() {
        return new SquealingDevil(this);
    }
}

class SquealingDevilEffect extends OneShotEffect {

    SquealingDevilEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. If you do, target creature gets +X/+0 until end of turn.";
    }

    SquealingDevilEffect(final SquealingDevilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl("{X}");
        if (player != null) {
            if (player.chooseUse(Outcome.BoostCreature, "Pay " + cost.getText() + "?", source, game)) {
                int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                cost.add(new GenericManaCost(costX));
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    Permanent permanent = game.getPermanent(source.getFirstTarget());
                    if (permanent != null && permanent.isCreature()) {
                        ContinuousEffect effect = new BoostTargetEffect(costX, 0, Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public SquealingDevilEffect copy() {
        return new SquealingDevilEffect(this);
    }

}
