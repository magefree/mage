package mage.cards.o;

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
public final class ObscuraStorefront extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Plains, Island, or Swamp card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate()
        ));
    }

    public ObscuraStorefront(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // When Obscura Storefront enters the battlefield, sacrifice it. When you do, search your library for a basic Plains, Island, or Swamp card, put it onto the battlefield tapped, then shuffle and you gain 1 life.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter), true
        ), false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeSourceCost().setText("sacrifice it"), null, false
        )));
    }

    private ObscuraStorefront(final ObscuraStorefront card) {
        super(card);
    }

    @Override
    public ObscuraStorefront copy() {
        return new ObscuraStorefront(this);
    }
}
