

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
public final class KjeldoranSkyknight extends CardImpl {

    public KjeldoranSkyknight (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private KjeldoranSkyknight(final KjeldoranSkyknight card) {
        super(card);
    }

    @Override
    public KjeldoranSkyknight copy() {
        return new KjeldoranSkyknight(this);
    }

}
