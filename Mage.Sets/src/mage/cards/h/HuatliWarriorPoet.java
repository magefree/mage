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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Mode;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DinosaurToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class HuatliWarriorPoet extends CardImpl {

    public HuatliWarriorPoet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: You gain life equal to the greatest power among creatures you control.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(new GreatestPowerAmongControlledCreaturesValue(), "You gain life equal to the greatest power among creatures you control"), 2));

        // 0: Create a 3/3 green Dinosaur creature token with trample.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DinosaurToken()), 0));

        // -X: Huatli, Warrior Poet deals X damage divided as you choose among any number of target creatures. Creatures dealt damage this way can't block this turn.
        Ability ability = new LoyaltyAbility(new HuatliWarriorPoetDamageEffect(new HuatliXValue()));
        ability.addTarget(new TargetCreaturePermanentAmount(new HuatliXValue()));
        this.addAbility(ability);
    }

    public HuatliWarriorPoet(final HuatliWarriorPoet card) {
        super(card);
    }

    @Override
    public HuatliWarriorPoet copy() {
        return new HuatliWarriorPoet(this);
    }
}

class HuatliXValue implements DynamicValue {

    private static final HuatliXValue defaultValue = new HuatliXValue();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                return ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return defaultValue;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static HuatliXValue getDefault() {
        return defaultValue;
    }
}

class HuatliWarriorPoetDamageEffect extends OneShotEffect {

    protected DynamicValue amount;

    public HuatliWarriorPoetDamageEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public HuatliWarriorPoetDamageEffect(final HuatliWarriorPoetDamageEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public HuatliWarriorPoetDamageEffect copy() {
        return new HuatliWarriorPoetDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null && permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, false, true) > 0) {
                    ContinuousEffect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{source} deals "
                + amount.toString()
                + " damage divided as you choose among any number of target "
                + mode.getTargets().get(0).getTargetName()
                + ". Creatures dealt damage this way can't block this turn";
    }
}
