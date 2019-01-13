package mage.cards.c;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoralCommando extends CardImpl {

    public CoralCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private CoralCommando(final CoralCommando card) {
        super(card);
    }

    @Override
    public CoralCommando copy() {
        return new CoralCommando(this);
    }
}
