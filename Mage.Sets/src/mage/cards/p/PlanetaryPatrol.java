package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class PlanetaryPatrol extends CardImpl {

    public PlanetaryPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land you control enters, this creature gets +1/+1 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private PlanetaryPatrol(final PlanetaryPatrol card) {
        super(card);
    }

    @Override
    public PlanetaryPatrol copy() {
        return new PlanetaryPatrol(this);
    }
}
