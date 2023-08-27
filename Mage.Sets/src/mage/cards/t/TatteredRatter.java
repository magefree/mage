package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TatteredRatter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.RAT, "a Rat you control");

    public TatteredRatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Rat you control becomes blocked, it gets +2/+0 until end of turn.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new BoostTargetEffect(2, 0, Duration.EndOfTurn),
                false, filter, true
        ));
    }

    private TatteredRatter(final TatteredRatter card) {
        super(card);
    }

    @Override
    public TatteredRatter copy() {
        return new TatteredRatter(this);
    }
}
