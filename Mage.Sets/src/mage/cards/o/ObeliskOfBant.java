

package mage.cards.o;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ObeliskOfBant extends CardImpl {

    public ObeliskOfBant (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private ObeliskOfBant(final ObeliskOfBant card) {
        super(card);
    }

    @Override
    public ObeliskOfBant copy() {
        return new ObeliskOfBant(this);
    }
}
