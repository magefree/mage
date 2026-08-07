package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class WarshipFlightCrew extends CardImpl {

    public WarshipFlightCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private WarshipFlightCrew(final WarshipFlightCrew card) {
        super(card);
    }

    @Override
    public WarshipFlightCrew copy() {
        return new WarshipFlightCrew(this);
    }
}
