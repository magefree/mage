

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class KnightsOfThorn extends CardImpl {

    public KnightsOfThorn (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private KnightsOfThorn(final KnightsOfThorn card) {
        super(card);
    }

    @Override
    public KnightsOfThorn copy() {
        return new KnightsOfThorn(this);
    }

}
