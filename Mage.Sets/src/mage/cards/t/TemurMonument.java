package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.Elephant55Token;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemurMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Forest, Island, or Mountain card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    public TemurMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When this artifact enters, search your library for a basic Forest, Island, or Mountain card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // {3}{G}{U}{R}, {T}, Sacrifice this artifact: Create a 5/5 green Elephant creature token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new Elephant55Token()), new ManaCostsImpl<>("{3}{G}{U}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TemurMonument(final TemurMonument card) {
        super(card);
    }

    @Override
    public TemurMonument copy() {
        return new TemurMonument(this);
    }
}
