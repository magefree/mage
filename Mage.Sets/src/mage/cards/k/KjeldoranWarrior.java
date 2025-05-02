

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
public final class KjeldoranWarrior extends CardImpl {

    public KjeldoranWarrior (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private KjeldoranWarrior(final KjeldoranWarrior card) {
        super(card);
    }

    @Override
    public KjeldoranWarrior copy() {
        return new KjeldoranWarrior(this);
    }

}
