package mage.cards.k;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfTheKeep extends CardImpl {

    public KnightOfTheKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private KnightOfTheKeep(final KnightOfTheKeep card) {
        super(card);
    }

    @Override
    public KnightOfTheKeep copy() {
        return new KnightOfTheKeep(this);
    }
}
