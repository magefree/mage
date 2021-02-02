package mage.cards.v;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VampireNoble extends CardImpl {

    public VampireNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private VampireNoble(final VampireNoble card) {
        super(card);
    }

    @Override
    public VampireNoble copy() {
        return new VampireNoble(this);
    }
}
