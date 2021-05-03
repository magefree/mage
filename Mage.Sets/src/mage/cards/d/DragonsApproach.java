package mage.cards.d;

import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonsApproach extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Dragon creature card");
    private static final FilterCard filter2 = new FilterCard("cards named Dragon's Approach");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter2.add(new NamePredicate("Dragon's Approach"));
    }

    public DragonsApproach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Dragon's Approach deals 3 damage to each opponent. You may exile Dragon's Approach and four cards named Dragon's Approach from your graveyard. If you do, search your library for a Dragon creature card, put it onto the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(3, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
                new CompositeCost(
                        new ExileSourceCost(), new ExileFromGraveCost(new TargetCardInYourGraveyard(4, filter2)),
                        "exile {this} and four cards named Dragon's Approach from your graveyard"
                )
        ));

        // A deck can have any number of cards named Dragon's Approach.
        this.getSpellAbility().addEffect(new InfoEffect(
                "A deck can have any number of cards named Dragon's Approach."
        ).concatBy("<br>"));
    }

    private DragonsApproach(final DragonsApproach card) {
        super(card);
    }

    @Override
    public DragonsApproach copy() {
        return new DragonsApproach(this);
    }
}
