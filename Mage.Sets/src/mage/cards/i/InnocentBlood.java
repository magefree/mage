
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public final class InnocentBlood extends CardImpl {
    

    public InnocentBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Each player sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(1, new FilterControlledCreaturePermanent("creature")));
    }

    public InnocentBlood(final InnocentBlood card) {
        super(card);
    }

    @Override
    public InnocentBlood copy() {
        return new InnocentBlood(this);
    }
}
