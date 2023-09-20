

package mage.cards.b;

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
public final class BenalishInfantry extends CardImpl {

    public BenalishInfantry (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private BenalishInfantry(final BenalishInfantry card) {
        super(card);
    }

    @Override
    public BenalishInfantry copy() {
        return new BenalishInfantry(this);
    }

}
