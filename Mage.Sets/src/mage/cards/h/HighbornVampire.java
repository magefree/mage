package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class HighbornVampire extends CardImpl {

    public HighbornVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private HighbornVampire(final HighbornVampire card) {
        super(card);
    }

    @Override
    public HighbornVampire copy() {
        return new HighbornVampire(this);
    }
}
