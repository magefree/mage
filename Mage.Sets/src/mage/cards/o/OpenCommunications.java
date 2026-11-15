package mage.cards.o;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author muz
 */
public final class OpenCommunications extends CardImpl {

    public OpenCommunications(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Beam me up {2}{U}
        this.addAbility(new BeamMeUpAbility(this, "{2}{U}"));
    }

    private OpenCommunications(final OpenCommunications card) {
        super(card);
    }

    @Override
    public OpenCommunications copy() {
        return new OpenCommunications(this);
    }
}
