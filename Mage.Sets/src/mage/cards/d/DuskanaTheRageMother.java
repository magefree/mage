package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import mage.filter.predicate.mageobject.BaseToughnessPredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class DuskanaTheRageMother extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control with base power and toughness 2/2");

    static {
        filter.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 2));
        filter.add(new BaseToughnessPredicate(ComparisonType.EQUAL_TO, 2));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DuskanaTheRageMother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Duskana, the Rage Mother enters the battlefield, draw a card for each creature you control with base power and toughness 2/2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter))));

        // Whenever a creature you control with base power and toughness 2/2 attacks, it gets +3/+3 until end of turn.
        this.addAbility(new AttacksAllTriggeredAbility(
                new BoostTargetEffect(3, 3), false, filter, SetTargetPointer.PERMANENT, false
        ));
    }

    private DuskanaTheRageMother(final DuskanaTheRageMother card) {
        super(card);
    }

    @Override
    public DuskanaTheRageMother copy() {
        return new DuskanaTheRageMother(this);
    }
}
