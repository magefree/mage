package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class InspiringCaptain extends CardImpl {

    public InspiringCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Inspiring Captain enters the battlefield, creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    public InspiringCaptain(final InspiringCaptain card) {
        super(card);
    }

    @Override
    public InspiringCaptain copy() {
        return new InspiringCaptain(this);
    }
}
