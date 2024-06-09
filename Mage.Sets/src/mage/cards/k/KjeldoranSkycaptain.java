

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class KjeldoranSkycaptain extends CardImpl {

    public KjeldoranSkycaptain (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private KjeldoranSkycaptain(final KjeldoranSkycaptain card) {
        super(card);
    }

    @Override
    public KjeldoranSkycaptain copy() {
        return new KjeldoranSkycaptain(this);
    }

}
