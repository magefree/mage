

package mage.cards.d;

import java.util.UUID;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author Loki
 */

public final class DarksteelIngot extends CardImpl {

    public DarksteelIngot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(IndestructibleAbility.getInstance());
        this.addAbility(new AnyColorManaAbility());
    }

    private DarksteelIngot(final DarksteelIngot card) {
        super(card);
    }

    @Override
    public DarksteelIngot copy() {
        return new DarksteelIngot(this);
    }
}