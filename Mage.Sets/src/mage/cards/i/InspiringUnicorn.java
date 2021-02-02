package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class InspiringUnicorn extends CardImpl {

    public InspiringUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Inspiring Unicorn attacks, creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), false
        ));
    }

    private InspiringUnicorn(final InspiringUnicorn card) {
        super(card);
    }

    @Override
    public InspiringUnicorn copy() {
        return new InspiringUnicorn(this);
    }
}
