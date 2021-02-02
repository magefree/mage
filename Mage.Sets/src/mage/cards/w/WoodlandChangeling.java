
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class WoodlandChangeling extends CardImpl {

    public WoodlandChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Changeling
        this.addAbility(new ChangelingAbility());
    }

    private WoodlandChangeling(final WoodlandChangeling card) {
        super(card);
    }

    @Override
    public WoodlandChangeling copy() {
        return new WoodlandChangeling(this);
    }
}
