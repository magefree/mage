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
public final class NagaEternal extends CardImpl {

    public NagaEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NAGA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private NagaEternal(final NagaEternal card) {
        super(card);
    }

    @Override
    public NagaEternal copy() {
        return new NagaEternal(this);
    }
}
