package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SilicateSurveyor extends CardImpl {

    public SilicateSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.THOLIAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)));
    }

    private SilicateSurveyor(final SilicateSurveyor card) {
        super(card);
    }

    @Override
    public SilicateSurveyor copy() {
        return new SilicateSurveyor(this);
    }
}
