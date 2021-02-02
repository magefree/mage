
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GluttonousZombie extends CardImpl {

    public GluttonousZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FearAbility.getInstance());
    }

    private GluttonousZombie(final GluttonousZombie card) {
        super(card);
    }

    @Override
    public GluttonousZombie copy() {
        return new GluttonousZombie(this);
    }
}
