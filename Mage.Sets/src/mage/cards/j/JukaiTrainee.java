package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
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
public final class JukaiTrainee extends CardImpl {

    public JukaiTrainee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Jukai Trainee blocks or becomes blocked, it gets +1/+1 until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn)
                        .setText("it gets +1/+1 until end of turn"),
                false, false
        ));
    }

    private JukaiTrainee(final JukaiTrainee card) {
        super(card);
    }

    @Override
    public JukaiTrainee copy() {
        return new JukaiTrainee(this);
    }
}
