
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class RavensCrime extends CardImpl {

    public RavensCrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Target player discards a card.
        this.getSpellAbility().getEffects().add(new DiscardTargetEffect(1));
        this.getSpellAbility().getTargets().add(new TargetPlayer());

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private RavensCrime(final RavensCrime card) {
        super(card);
    }

    @Override
    public RavensCrime copy() {
        return new RavensCrime(this);
    }
}
