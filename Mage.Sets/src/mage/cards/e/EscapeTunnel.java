package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscapeTunnel extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public EscapeTunnel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Sacrifice Escape Tunnel: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {T}, Sacrifice Escape Tunnel: Target creature with power 2 or less can't be blocked this turn.
        ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EscapeTunnel(final EscapeTunnel card) {
        super(card);
    }

    @Override
    public EscapeTunnel copy() {
        return new EscapeTunnel(this);
    }
}
