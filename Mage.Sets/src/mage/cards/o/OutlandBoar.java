package mage.cards.o;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OutlandBoar extends CardImpl {

    public OutlandBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Outland Boar can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private OutlandBoar(final OutlandBoar card) {
        super(card);
    }

    @Override
    public OutlandBoar copy() {
        return new OutlandBoar(this);
    }
}
