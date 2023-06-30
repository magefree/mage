package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DinosaurToken;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuatliWarriorPoet extends CardImpl {

    public HuatliWarriorPoet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);

        this.setStartingLoyalty(3);

        // +2: You gain life equal to the greatest power among creatures you control.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(
                GreatestPowerAmongControlledCreaturesValue.instance,
                "You gain life equal to the greatest power among creatures you control"
        ), 2));

        // 0: Create a 3/3 green Dinosaur creature token with trample.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DinosaurToken()), 0));

        // -X: Huatli, Warrior Poet deals X damage divided as you choose among any number of target creatures. Creatures dealt damage this way can't block this turn.
        Ability ability = new LoyaltyAbility(new HuatliWarriorPoetDamageEffect());
        ability.addTarget(new TargetCreaturePermanentAmount(GetXLoyaltyValue.instance));
        this.addAbility(ability);
    }

    private HuatliWarriorPoet(final HuatliWarriorPoet card) {
        super(card);
    }

    @Override
    public HuatliWarriorPoet copy() {
        return new HuatliWarriorPoet(this);
    }
}

class HuatliWarriorPoetDamageEffect extends OneShotEffect {

    HuatliWarriorPoetDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage divided as you choose among any number of target creatures. Creatures dealt damage this way can't block this turn";
    }

    private HuatliWarriorPoetDamageEffect(final HuatliWarriorPoetDamageEffect effect) {
        super(effect);
    }

    @Override
    public HuatliWarriorPoetDamageEffect copy() {
        return new HuatliWarriorPoetDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().isEmpty()) {
            return true;
        }
        Target multiTarget = source.getTargets().get(0);
        for (UUID target : multiTarget.getTargets()) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null && permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), source, game, false, true) > 0) {
                ContinuousEffect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
