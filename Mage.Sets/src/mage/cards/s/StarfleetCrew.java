package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class StarfleetCrew extends CardImpl {

    public StarfleetCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private StarfleetCrew(final StarfleetCrew card) {
        super(card);
    }

    @Override
    public StarfleetCrew copy() {
        return new StarfleetCrew(this);
    }
}
