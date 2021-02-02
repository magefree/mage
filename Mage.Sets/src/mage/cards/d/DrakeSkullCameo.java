
package mage.cards.d;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class DrakeSkullCameo extends CardImpl {

    public DrakeSkullCameo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private DrakeSkullCameo(final DrakeSkullCameo card) {
        super(card);
    }

    @Override
    public DrakeSkullCameo copy() {
        return new DrakeSkullCameo(this);
    }
}
