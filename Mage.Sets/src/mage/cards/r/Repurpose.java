
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author Styxo
 */
public final class Repurpose extends CardImpl {

    public Repurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Return up to one target creature card from graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("Return up to one target creature card from graveyard to your hand.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private Repurpose(final Repurpose card) {
        super(card);
    }

    @Override
    public Repurpose copy() {
        return new Repurpose(this);
    }
}
