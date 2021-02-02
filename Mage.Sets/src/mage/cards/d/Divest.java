
package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

/**
 *
 * @author MasterSamurai
 *
 */
public final class Divest extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact or creature card");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public Divest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target player reveals their hand. You choose an artifact or creature card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
    }

    private Divest(final Divest card) {
        super(card);
    }

    @Override
    public Divest copy() {
        return new Divest(this);
    }
}