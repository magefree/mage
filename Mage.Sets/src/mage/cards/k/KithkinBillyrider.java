package mage.cards.k;

import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KithkinBillyrider extends CardImpl {

    public KithkinBillyrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private KithkinBillyrider(final KithkinBillyrider card) {
        super(card);
    }

    @Override
    public KithkinBillyrider copy() {
        return new KithkinBillyrider(this);
    }
}
