

package mage.cards.m;

import java.util.UUID;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class Manalith extends CardImpl {

    public Manalith (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new AnyColorManaAbility());
    }

    private Manalith(final Manalith card) {
        super(card);
    }

    @Override
    public Manalith copy() {
        return new Manalith(this);
    }

}
