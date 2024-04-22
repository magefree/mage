package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNoncreatureCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class ArcumDagsson extends CardImpl {

    private static final FilterCard filter = new FilterNoncreatureCard("noncreature artifact card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ArcumDagsson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Target artifact creature's controller sacrifices it. That player may search their library for a noncreature artifact card, put it onto the battlefield, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SacrificeTargetEffect(), new TapSourceCost());
        ability.addEffect(new SearchLibraryPutInPlayTargetControllerEffect(new TargetCardInLibrary(filter), false, Outcome.PutCardInPlay, "that player"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE));
        this.addAbility(ability);
    }

    private ArcumDagsson(final ArcumDagsson card) {
        super(card);
    }

    @Override
    public ArcumDagsson copy() {
        return new ArcumDagsson(this);
    }
}
