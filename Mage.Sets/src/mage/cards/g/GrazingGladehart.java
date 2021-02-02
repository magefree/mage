
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GrazingGladehart extends CardImpl {

    public GrazingGladehart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ANTELOPE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new LandfallAbility(new GainLifeEffect(2), true));
    }

    private GrazingGladehart(final GrazingGladehart card) {
        super(card);
    }

    @Override
    public GrazingGladehart copy() {
        return new GrazingGladehart(this);
    }
}
