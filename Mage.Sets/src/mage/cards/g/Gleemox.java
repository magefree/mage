

package mage.cards.g;

import java.util.UUID;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author Tuomas-Matti Soikkeli
 */

public final class Gleemox extends CardImpl {

    public Gleemox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");
        this.addAbility(new AnyColorManaAbility());
    }

    private Gleemox(final Gleemox card) {
        super(card);
    }

    @Override
    public Gleemox copy() {
        return new Gleemox(this);
    }
}