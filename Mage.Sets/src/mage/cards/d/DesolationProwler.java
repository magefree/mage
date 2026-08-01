package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class DesolationProwler extends CardImpl {

    public DesolationProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Pay 2 life: This creature gets +2/+2 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
            Zone.BATTLEFIELD,
            new BoostSourceEffect(2, 2, Duration.EndOfTurn),
            new PayLifeCost(2)
        ));
    }

    private DesolationProwler(final DesolationProwler card) {
        super(card);
    }

    @Override
    public DesolationProwler copy() {
        return new DesolationProwler(this);
    }
}
