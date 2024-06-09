

package mage.cards.o;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ObeliskOfEsper extends CardImpl {

    public ObeliskOfEsper (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private ObeliskOfEsper(final ObeliskOfEsper card) {
        super(card);
    }

    @Override
    public ObeliskOfEsper copy() {
        return new ObeliskOfEsper(this);
    }
}
