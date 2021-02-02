
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class NipGwyllion extends CardImpl {

    public NipGwyllion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/B}");
        this.subtype.add(SubType.HAG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(LifelinkAbility.getInstance());
    }

    private NipGwyllion(final NipGwyllion card) {
        super(card);
    }

    @Override
    public NipGwyllion copy() {
        return new NipGwyllion(this);
    }
}
