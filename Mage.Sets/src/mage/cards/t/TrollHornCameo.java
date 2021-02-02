
package mage.cards.t;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class TrollHornCameo extends CardImpl {

    public TrollHornCameo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private TrollHornCameo(final TrollHornCameo card) {
        super(card);
    }

    @Override
    public TrollHornCameo copy() {
        return new TrollHornCameo(this);
    }
}
