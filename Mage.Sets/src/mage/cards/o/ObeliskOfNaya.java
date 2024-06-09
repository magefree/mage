

package mage.cards.o;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ObeliskOfNaya extends CardImpl {

    public ObeliskOfNaya (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private ObeliskOfNaya(final ObeliskOfNaya card) {
        super(card);
    }

    @Override
    public ObeliskOfNaya copy() {
        return new ObeliskOfNaya(this);
    }
}
