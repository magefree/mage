package mage.cards.v;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoyageHome extends CardImpl {

    public VoyageHome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{U}");

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // You draw three cards and gain 3 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3, true));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).setText("and gain 3 life"));
    }

    private VoyageHome(final VoyageHome card) {
        super(card);
    }

    @Override
    public VoyageHome copy() {
        return new VoyageHome(this);
    }
}
