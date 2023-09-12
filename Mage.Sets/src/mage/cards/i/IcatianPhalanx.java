

package mage.cards.i;

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
public final class IcatianPhalanx extends CardImpl {

    public IcatianPhalanx (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private IcatianPhalanx(final IcatianPhalanx card) {
        super(card);
    }

    @Override
    public IcatianPhalanx copy() {
        return new IcatianPhalanx(this);
    }

}
