package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class AttendantOfVraska extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Vraska planeswalker");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.VRASKA));
    }

    public AttendantOfVraska(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Attendant of Vraska dies, if you control a Vraska planeswalker, you gain life equal to Attendant of Vraska's power.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesTriggeredAbility(new GainLifeEffect(
                        new SourcePermanentPowerCount()
                ), false), new PermanentsOnTheBattlefieldCondition(filter),
                "When {this} dies, if you control a Vraska planeswalker, "
                + "you gain life equal to {this}'s power."
        ));
    }

    public AttendantOfVraska(final AttendantOfVraska card) {
        super(card);
    }

    @Override
    public AttendantOfVraska copy() {
        return new AttendantOfVraska(this);
    }
}
