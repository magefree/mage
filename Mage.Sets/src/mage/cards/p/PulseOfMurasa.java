
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class PulseOfMurasa extends CardImpl {

    private static final FilterCard FILTER = new FilterCard("creature or land card from a graveyard");

    static {
        FILTER.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public PulseOfMurasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Return target creature or land card from a graveyard to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(FILTER));
        // You gain 6 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(6));
    }

    private PulseOfMurasa(final PulseOfMurasa card) {
        super(card);
    }

    @Override
    public PulseOfMurasa copy() {
        return new PulseOfMurasa(this);
    }
}
