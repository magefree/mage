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
public final class NyxbornColossus extends CardImpl {

    public NyxbornColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}{G}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);
    }

    private NyxbornColossus(final NyxbornColossus card) {
        super(card);
    }

    @Override
    public NyxbornColossus copy() {
        return new NyxbornColossus(this);
    }
}
