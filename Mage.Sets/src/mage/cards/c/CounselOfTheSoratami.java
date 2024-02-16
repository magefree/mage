

package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class CounselOfTheSoratami extends CardImpl {

    public CounselOfTheSoratami (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private CounselOfTheSoratami(final CounselOfTheSoratami card) {
        super(card);
    }

    @Override
    public CounselOfTheSoratami copy() {
        return new CounselOfTheSoratami(this);
    }

}
