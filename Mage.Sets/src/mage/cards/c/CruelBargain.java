
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseHalfLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author magenoxx
 */
public final class CruelBargain extends CardImpl {

    public CruelBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}{B}");


        // Draw four cards. You lose half your life, rounded up.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        this.getSpellAbility().addEffect(new LoseHalfLifeEffect());
    }

    private CruelBargain(final CruelBargain card) {
        super(card);
    }

    @Override
    public CruelBargain copy() {
        return new CruelBargain(this);
    }
}
