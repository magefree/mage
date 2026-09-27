package mage.cards.s;

import java.util.UUID;

import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class SphinxsApproach extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Sphinx creature card");
    private static final FilterCard filter2 = new FilterCard("cards named Sphinx's Approach");
    private static final Hint hint = new ValueHint(
            "Cards named Sphinx's Approach in your graveyard", new CardsInControllerGraveyardCount(filter2)
    );

    static {
        filter.add(SubType.SPHINX.getPredicate());
        filter2.add(new NamePredicate("Sphinx's Approach"));
    }

    public SphinxsApproach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        
        // Draw two cards. Then you may exile this spell and four cards named Sphinx's Approach from your graveyard. If you do, search your library for a Sphinx creature card, put it onto the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
            new CompositeCost(
                new ExileSourceCost(), new ExileFromGraveCost(new TargetCardInYourGraveyard(4, filter2)),
                "exile this spell and four cards named Sphinx's Approach from your graveyard"
            )
        ));
        this.getSpellAbility().addHint(hint);        

        // A deck can have any number of cards named Sphinx's Approach.
        this.getSpellAbility().addEffect(new InfoEffect(
            "A deck can have any number of cards named Sphinx's Approach."
        ).concatBy("<br>"));
    }

    private SphinxsApproach(final SphinxsApproach card) {
        super(card);
    }

    @Override
    public SphinxsApproach copy() {
        return new SphinxsApproach(this);
    }
}
