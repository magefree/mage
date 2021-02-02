
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class Thoughtcast extends CardImpl {

    public Thoughtcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");


        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private Thoughtcast(final Thoughtcast card) {
        super(card);
    }

    @Override
    public Thoughtcast copy() {
        return new Thoughtcast(this);
    }
}
