package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PricklyMarmoset extends CardImpl {

    public PricklyMarmoset(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you cycle a card, Prickly Marmoset gets +2/+0 until end of turn.
        this.addAbility(new CycleControllerTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn)
        ));
    }

    private PricklyMarmoset(final PricklyMarmoset card) {
        super(card);
    }

    @Override
    public PricklyMarmoset copy() {
        return new PricklyMarmoset(this);
    }
}
