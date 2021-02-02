
package mage.cards.t;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class TigereyeCameo extends CardImpl {

    public TigereyeCameo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private TigereyeCameo(final TigereyeCameo card) {
        super(card);
    }

    @Override
    public TigereyeCameo copy() {
        return new TigereyeCameo(this);
    }
}
