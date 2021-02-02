
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SpectralRider extends CardImpl {

    public SpectralRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(IntimidateAbility.getInstance());
    }

    private SpectralRider(final SpectralRider card) {
        super(card);
    }

    @Override
    public SpectralRider copy() {
        return new SpectralRider(this);
    }
}
