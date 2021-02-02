package mage.cards.s;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ShrineKeeper extends CardImpl {

    public ShrineKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private ShrineKeeper(final ShrineKeeper card) {
        super(card);
    }

    @Override
    public ShrineKeeper copy() {
        return new ShrineKeeper(this);
    }
}
