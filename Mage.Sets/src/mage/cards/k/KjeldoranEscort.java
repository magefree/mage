

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class KjeldoranEscort extends CardImpl {

    public KjeldoranEscort (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private KjeldoranEscort(final KjeldoranEscort card) {
        super(card);
    }

    @Override
    public KjeldoranEscort copy() {
        return new KjeldoranEscort(this);
    }

}
