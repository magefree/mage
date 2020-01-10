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
public final class NyxbornBrute extends CardImpl {

    public NyxbornBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(7);
        this.toughness = new MageInt(3);
    }

    private NyxbornBrute(final NyxbornBrute card) {
        super(card);
    }

    @Override
    public NyxbornBrute copy() {
        return new NyxbornBrute(this);
    }
}
