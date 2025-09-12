package mage.cards.c;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class CatapultFodder extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "you control three or more creatures that each have toughness greater than their power"
    );

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ValueHint(
            "Creatures you control with toughness greater than their power", new PermanentsOnBattlefieldCount(filter)
    );

    public CatapultFodder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.c.CatapultCaptain.class;

        // At the beginning of combat on your turn, if you control three or more creatures that each have toughness greater than their power, transform Catapult Fodder.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new TransformSourceEffect()).withInterveningIf(condition));
    }

    private CatapultFodder(final CatapultFodder card) {
        super(card);
    }

    @Override
    public CatapultFodder copy() {
        return new CatapultFodder(this);
    }
}
