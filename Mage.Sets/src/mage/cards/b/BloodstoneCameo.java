
package mage.cards.b;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class BloodstoneCameo extends CardImpl {

    public BloodstoneCameo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private BloodstoneCameo(final BloodstoneCameo card) {
        super(card);
    }

    @Override
    public BloodstoneCameo copy() {
        return new BloodstoneCameo(this);
    }
}
