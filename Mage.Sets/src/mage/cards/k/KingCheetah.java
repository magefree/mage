
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KingCheetah extends CardImpl {

    public KingCheetah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FlashAbility.getInstance());
    }

    private KingCheetah(final KingCheetah card) {
        super(card);
    }

    @Override
    public KingCheetah copy() {
        return new KingCheetah(this);
    }
}
