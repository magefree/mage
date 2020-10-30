package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Mode;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
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

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // +2: You gain life equal to the greatest power among creatures you control.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(
                GreatestPowerAmongControlledCreaturesValue.instance,
                "You gain life equal to the greatest power among creatures you control"
        ), 2));

        // 0: Create a 3/3 green Dinosaur creature token with trample.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DinosaurToken()), 0));

        // -X: Huatli, Warrior Poet deals X damage divided as you choose among any number of target creatures. Creatures dealt damage this way can't block this turn.
        Ability ability = new LoyaltyAbility(new HuatliWarriorPoetDamageEffect(HuatliXValue.instance));
        ability.addTarget(new TargetCreaturePermanentAmount(HuatliXValue.instance));
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

enum HuatliXValue implements DynamicValue {
    instance;

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
        return instance;
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
        return instance;
    }
}

class HuatliWarriorPoetDamageEffect extends OneShotEffect {

    protected DynamicValue amount;

    HuatliWarriorPoetDamageEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    private HuatliWarriorPoetDamageEffect(final HuatliWarriorPoetDamageEffect effect) {
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
        return "{this} deals "
                + amount.toString()
                + " damage divided as you choose among any number of target "
                + mode.getTargets().get(0).getTargetName()
                + ". Creatures dealt damage this way can't block this turn";
    }
}
