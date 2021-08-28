package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SkeletonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkeletalSwarming extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SKELETON);
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.SKELETON, "");
    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(filter), StaticValue.get(-1)
    );

    public SkeletalSwarming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{G}");

        // Each Skeleton you control has trample, attacks each combat if able, and gets +X/+0, where X is the number of other Skeletons you control.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("each Skeleton you control has trample"));
        ability.addEffect(new AttacksIfAbleAllEffect(filter2).setText(", attacks each combat if able"));
        ability.addEffect(new BoostControlledEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield, filter2, false
        ).setText(", and gets +X/+0, where X is the number of other Skeletons you control"));
        this.addAbility(ability);

        // At the beginning of your end step, create a tapped 1/1 black Skeleton creature token. If a creature died this turn, create two of those tokens instead.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new CreateTokenEffect(new SkeletonToken(), 2, true, false),
                        new CreateTokenEffect(new SkeletonToken(), 1, true, false),
                        MorbidCondition.instance, "create a tapped 1/1 black Skeleton creature token. " +
                        "If a creature died this turn, create two of those tokens instead"
                ), TargetController.YOU, false
        ).addHint(MorbidHint.instance));
    }

    private SkeletalSwarming(final SkeletalSwarming card) {
        super(card);
    }

    @Override
    public SkeletalSwarming copy() {
        return new SkeletalSwarming(this);
    }
}
