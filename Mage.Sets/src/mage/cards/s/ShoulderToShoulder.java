
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class ShoulderToShoulder extends CardImpl {

    public ShoulderToShoulder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Support 2.
        getSpellAbility().addEffect(new SupportEffect(this, 2, false));

        // Draw a card.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ShoulderToShoulder(final ShoulderToShoulder card) {
        super(card);
    }

    @Override
    public ShoulderToShoulder copy() {
        return new ShoulderToShoulder(this);
    }
}
