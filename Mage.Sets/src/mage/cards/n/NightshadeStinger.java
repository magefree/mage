
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class NightshadeStinger extends CardImpl {

    public NightshadeStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        // Nightshade Stinger can't block.
        this.addAbility(new CantBlockAbility());
    }

    private NightshadeStinger(final NightshadeStinger card) {
        super(card);
    }

    @Override
    public NightshadeStinger copy() {
        return new NightshadeStinger(this);
    }
}
