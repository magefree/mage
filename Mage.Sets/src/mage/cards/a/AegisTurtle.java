package mage.cards.a;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AegisTurtle extends CardImpl {

    public AegisTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);
    }

    private AegisTurtle(final AegisTurtle card) {
        super(card);
    }

    @Override
    public AegisTurtle copy() {
        return new AegisTurtle(this);
    }
}
