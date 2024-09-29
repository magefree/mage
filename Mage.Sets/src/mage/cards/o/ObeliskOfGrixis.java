

package mage.cards.o;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ObeliskOfGrixis extends CardImpl {

    public ObeliskOfGrixis (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private ObeliskOfGrixis(final ObeliskOfGrixis card) {
        super(card);
    }

    @Override
    public ObeliskOfGrixis copy() {
        return new ObeliskOfGrixis(this);
    }
}
