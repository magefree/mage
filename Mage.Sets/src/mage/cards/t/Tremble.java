
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author cbt33, jonubuu (Innocent Blood)
 */
public final class Tremble extends CardImpl {

    public Tremble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Each player sacrifices a land.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(1, new FilterControlledLandPermanent("land")));
    }

    private Tremble(final Tremble card) {
        super(card);
    }

    @Override
    public Tremble copy() {
        return new Tremble(this);
    }
}
