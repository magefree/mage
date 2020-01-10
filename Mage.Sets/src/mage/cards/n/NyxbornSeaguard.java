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
public final class NyxbornSeaguard extends CardImpl {

    public NyxbornSeaguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
    }

    private NyxbornSeaguard(final NyxbornSeaguard card) {
        super(card);
    }

    @Override
    public NyxbornSeaguard copy() {
        return new NyxbornSeaguard(this);
    }
}
