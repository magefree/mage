package mage.cards.s;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import mage.filter.predicate.mageobject.BaseToughnessPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordOfTheSqueak extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with base power or toughness 1");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("a Hamster, Mouse, Rat, or Squirrel you control");

    static {
        filter.add(Predicates.or(
                new BasePowerPredicate(ComparisonType.EQUAL_TO, 1),
                new BaseToughnessPredicate(ComparisonType.EQUAL_TO, 1)
        ));
        filter2.add(Predicates.or(
                SubType.HAMSTER.getPredicate(),
                SubType.MOUSE.getPredicate(),
                SubType.RAT.getPredicate(),
                SubType.SQUIRREL.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);

    public SwordOfTheSqueak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each creature you control with base power or toughness 1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)));

        // Whenever a Hamster, Mouse, Rat, or Squirrel you control enters, you may attach Sword of the Squeak to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature, "attach {this} to that creature"),
                filter2, true, SetTargetPointer.PERMANENT
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private SwordOfTheSqueak(final SwordOfTheSqueak card) {
        super(card);
    }

    @Override
    public SwordOfTheSqueak copy() {
        return new SwordOfTheSqueak(this);
    }
}
