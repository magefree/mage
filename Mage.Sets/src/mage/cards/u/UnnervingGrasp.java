package mage.cards.u;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnnervingGrasp extends CardImpl {

    public UnnervingGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Return up to one target nonland permanent to its owner's hand. Manifest dread.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(0, 1));
        this.getSpellAbility().addEffect(new ManifestDreadEffect());
    }

    private UnnervingGrasp(final UnnervingGrasp card) {
        super(card);
    }

    @Override
    public UnnervingGrasp copy() {
        return new UnnervingGrasp(this);
    }
}
