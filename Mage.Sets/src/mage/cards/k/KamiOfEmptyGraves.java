
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KamiOfEmptyGraves extends CardImpl {

    public KamiOfEmptyGraves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        this.addAbility(new SoulshiftAbility(3));
    }

    private KamiOfEmptyGraves(final KamiOfEmptyGraves card) {
        super(card);
    }

    @Override
    public KamiOfEmptyGraves copy() {
        return new KamiOfEmptyGraves(this);
    }
}
