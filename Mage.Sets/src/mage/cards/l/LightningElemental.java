

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LightningElemental extends CardImpl {

    public LightningElemental (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    private LightningElemental(final LightningElemental card) {
        super(card);
    }

    @Override
    public LightningElemental copy() {
        return new LightningElemental(this);
    }

}
