
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseHalfLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class InfernalContract extends CardImpl {

    public InfernalContract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}{B}");


        // Draw four cards. You lose half your life, rounded up.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        this.getSpellAbility().addEffect(new LoseHalfLifeEffect());
    }

    private InfernalContract(final InfernalContract card) {
        super(card);
    }

    @Override
    public InfernalContract copy() {
        return new InfernalContract(this);
    }
}

