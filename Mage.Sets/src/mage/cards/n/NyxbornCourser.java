package mage.cards.n;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class NyxbornCourser extends CardImpl {

    public NyxbornCourser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
    }

    private NyxbornCourser(final NyxbornCourser card) {
        super(card);
    }

    @Override
    public NyxbornCourser copy() {
        return new NyxbornCourser(this);
    }
}
