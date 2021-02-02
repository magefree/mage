
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HarvestGwyllion extends CardImpl {

    public HarvestGwyllion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W/B}{W/B}");
        this.subtype.add(SubType.HAG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(WitherAbility.getInstance());
    }

    private HarvestGwyllion(final HarvestGwyllion card) {
        super(card);
    }

    @Override
    public HarvestGwyllion copy() {
        return new HarvestGwyllion(this);
    }
}
