

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SeveredLegion extends CardImpl {

    public SeveredLegion (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FearAbility.getInstance());
    }

    private SeveredLegion(final SeveredLegion card) {
        super(card);
    }

    @Override
    public SeveredLegion copy() {
        return new SeveredLegion(this);
    }

}
