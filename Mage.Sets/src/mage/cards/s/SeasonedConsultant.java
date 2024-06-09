package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeasonedConsultant extends CardImpl {

    public SeasonedConsultant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you attack with three or more creatures, Seasoned Consultant gets +2/+0 until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), 3
        ));
    }

    private SeasonedConsultant(final SeasonedConsultant card) {
        super(card);
    }

    @Override
    public SeasonedConsultant copy() {
        return new SeasonedConsultant(this);
    }
}
