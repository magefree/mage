package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class TimidDrake extends CardImpl {

    public TimidDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When another creature enters the battlefield, return Timid Drake to its owner's hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true),
                StaticFilters.FILTER_ANOTHER_CREATURE, false,
                "When another creature enters the battlefield, return {this} to its owner's hand."
        ));
    }

    private TimidDrake(final TimidDrake card) {
        super(card);
    }

    @Override
    public TimidDrake copy() {
        return new TimidDrake(this);
    }
}
