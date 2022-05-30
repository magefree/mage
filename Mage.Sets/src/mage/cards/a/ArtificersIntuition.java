package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ArtificersIntuition extends CardImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("artifact card with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public ArtificersIntuition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // {U}, Discard an artifact card: Search your library for an artifact card with converted mana cost 1 or less, reveal that card, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), new ManaCostsImpl<>("{U}"));
        ability.addCost(new DiscardCardCost(StaticFilters.FILTER_CARD_ARTIFACT_AN));
        this.addAbility(ability);
    }

    private ArtificersIntuition(final ArtificersIntuition card) {
        super(card);
    }

    @Override
    public ArtificersIntuition copy() {
        return new ArtificersIntuition(this);
    }
}
