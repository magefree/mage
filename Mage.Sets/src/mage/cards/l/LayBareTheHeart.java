package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class LayBareTheHeart extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonlegendary, nonland card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public LayBareTheHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a nonlegendary, nonland card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
    }

    private LayBareTheHeart(final LayBareTheHeart card) {
        super(card);
    }

    @Override
    public LayBareTheHeart copy() {
        return new LayBareTheHeart(this);
    }
}
