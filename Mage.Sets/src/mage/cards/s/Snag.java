
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class Snag extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Forest card");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("unblocked creatures");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter2.add(Predicates.not(BlockedPredicate.instance));
    }

    public Snag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // You may discard a Forest card rather than pay Snag's mana cost.
        Ability ability = new AlternativeCostSourceAbility(new DiscardTargetCost(new TargetCardInHand(filter)));
        this.addAbility(ability);

        // Prevent all combat damage that would be dealt by unblocked creatures this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter2, Duration.EndOfTurn, true));
    }

    private Snag(final Snag card) {
        super(card);
    }

    @Override
    public Snag copy() {
        return new Snag(this);
    }
}
