

package mage.cards.o;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ObeliskOfJund extends CardImpl {

    public ObeliskOfJund (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private ObeliskOfJund(final ObeliskOfJund card) {
        super(card);
    }

    @Override
    public ObeliskOfJund copy() {
        return new ObeliskOfJund(this);
    }
}
