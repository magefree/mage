
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class SeashellCameo extends CardImpl {

    public SeashellCameo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private SeashellCameo(final SeashellCameo card) {
        super(card);
    }

    @Override
    public SeashellCameo copy() {
        return new SeashellCameo(this);
    }
}
