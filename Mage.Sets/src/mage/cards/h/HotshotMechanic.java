package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.CrewIncreasedPowerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HotshotMechanic extends CardImpl {

    public HotshotMechanic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Hotshot Mechanic crews Vehicles as though its power were 2 greater.
        this.addAbility(new CrewIncreasedPowerAbility());
    }

    private HotshotMechanic(final HotshotMechanic card) {
        super(card);
    }

    @Override
    public HotshotMechanic copy() {
        return new HotshotMechanic(this);
    }
}
