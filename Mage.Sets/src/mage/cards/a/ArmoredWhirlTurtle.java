package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class ArmoredWhirlTurtle extends CardImpl {

    public ArmoredWhirlTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);
    }

    private ArmoredWhirlTurtle(final ArmoredWhirlTurtle card) {
        super(card);
    }

    @Override
    public ArmoredWhirlTurtle copy() {
        return new ArmoredWhirlTurtle(this);
    }
}
