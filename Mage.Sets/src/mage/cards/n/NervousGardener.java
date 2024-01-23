package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NervousGardener extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("a land card with a basic land type");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public NervousGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Disguise {G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{G}")));

        // When Nervous Gardener is turned face up, search your library for a land card with a basic land type, reveal it, put it into your hand, then shuffle.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));
    }

    private NervousGardener(final NervousGardener card) {
        super(card);
    }

    @Override
    public NervousGardener copy() {
        return new NervousGardener(this);
    }
}
