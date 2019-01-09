package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrowthChamberGuardian extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("a card named Growth-Chamber Guardian");

    static {
        filter.add(new NamePredicate("Growth-Chamber Guardian"));
    }

    public GrowthChamberGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{G}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{2}{G}"));

        // Whenever one or more +1/+1 counters are put on Growth-Chamber Guardian, you may search your library for a card named Growth-Chamber Guardian, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true));
    }

    private GrowthChamberGuardian(final GrowthChamberGuardian card) {
        super(card);
    }

    @Override
    public GrowthChamberGuardian copy() {
        return new GrowthChamberGuardian(this);
    }
}
