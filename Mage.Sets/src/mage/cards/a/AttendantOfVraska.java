package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AttendantOfVraska extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPlaneswalkerPermanent(SubType.VRASKA, "you control a Vraska planeswalker")
    );

    public AttendantOfVraska(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Attendant of Vraska dies, if you control a Vraska planeswalker, you gain life equal to Attendant of Vraska's power.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                .setText("you gain life equal to {this}'s power"), false).withInterveningIf(condition));
    }

    private AttendantOfVraska(final AttendantOfVraska card) {
        super(card);
    }

    @Override
    public AttendantOfVraska copy() {
        return new AttendantOfVraska(this);
    }
}
