package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClockworkPercussionist extends CardImpl {

    public ClockworkPercussionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.MONKEY);
        this.subtype.add(SubType.TOY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Clockwork Percussionist dies, exile the top card of your library. You may play it until the end of your next turn.
        this.addAbility(new DiesSourceTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
                .withTextOptions("it", true)));
    }

    private ClockworkPercussionist(final ClockworkPercussionist card) {
        super(card);
    }

    @Override
    public ClockworkPercussionist copy() {
        return new ClockworkPercussionist(this);
    }
}
