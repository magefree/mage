package mage.cards.a;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.IllusionArtifactToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class AdversaryByAlgorithm extends CardImpl {

    public AdversaryByAlgorithm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Create a 3/3 colorless Illusion artifact creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new IllusionArtifactToken()));

        // Beam me up {3}{G}
        this.addAbility(new BeamMeUpAbility(this, "{3}{G}"));
    }

    private AdversaryByAlgorithm(final AdversaryByAlgorithm card) {
        super(card);
    }

    @Override
    public AdversaryByAlgorithm copy() {
        return new AdversaryByAlgorithm(this);
    }
}
