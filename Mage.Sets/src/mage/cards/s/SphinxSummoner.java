
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class SphinxSummoner extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("artifact creature card");

    static {
        filter.add(Predicates.and(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public SphinxSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}{B}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Sphinx Summoner enters the battlefield, you may search your library for an artifact creature card, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true));
    }

    private SphinxSummoner(final SphinxSummoner card) {
        super(card);
    }

    @Override
    public SphinxSummoner copy() {
        return new SphinxSummoner(this);
    }
}
