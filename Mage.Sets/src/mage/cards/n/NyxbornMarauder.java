package mage.cards.n;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyxbornMarauder extends CardImpl {

    public NyxbornMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private NyxbornMarauder(final NyxbornMarauder card) {
        super(card);
    }

    @Override
    public NyxbornMarauder copy() {
        return new NyxbornMarauder(this);
    }
}
