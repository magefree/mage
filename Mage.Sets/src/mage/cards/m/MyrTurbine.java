
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.MyrToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class MyrTurbine extends CardImpl {

    private static final FilterCreatureCard filterCard = new FilterCreatureCard("Myr creature card");
    private static final FilterControlledPermanent filterMyr = new FilterControlledPermanent("untapped Myr you control");

    static {
        filterCard.add(SubType.MYR.getPredicate());
        filterMyr.add(TappedPredicate.UNTAPPED);
        filterMyr.add(SubType.MYR.getPredicate());
    }

    public MyrTurbine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new MyrToken()),
                new TapSourceCost()));
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterCard)),
                new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(5, 5, filterMyr, true)));
        this.addAbility(ability);
    }

    private MyrTurbine(final MyrTurbine card) {
        super(card);
    }

    @Override
    public MyrTurbine copy() {
        return new MyrTurbine(this);
    }
}
