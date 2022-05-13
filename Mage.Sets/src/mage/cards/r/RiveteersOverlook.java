package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiveteersOverlook extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Swamp, Mountain, or Forest card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public RiveteersOverlook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // When Riveteers Overlook enters the battlefield, sacrifice it. When you do, search your library for a basic Swamp, Mountain, or Forest card, put it onto the battlefield tapped, then shuffle and you gain 1 life.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter), true, true
        ), false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeSourceCost().setText("sacrifice it"), null, false
        )));
    }

    private RiveteersOverlook(final RiveteersOverlook card) {
        super(card);
    }

    @Override
    public RiveteersOverlook copy() {
        return new RiveteersOverlook(this);
    }
}
