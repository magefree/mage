package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamshaperShaman extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("nonland permanent card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public DreamshaperShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, you may pay {2}{R} and sacrifice a nonland permanent. If you do, reveal cards from the top of your library until you reveal a nonland permanent card. Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new RevealCardsFromLibraryUntilEffect(
                        filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
                ),
                new CompositeCost(
                        new ManaCostsImpl<>("{2}{R}"),
                        new SacrificeTargetCost(new TargetControlledPermanent(
                                StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND
                        )), "pay {2}{R} and sacrifice a nonland permanent"
                )
        ), TargetController.YOU, false));
    }

    private DreamshaperShaman(final DreamshaperShaman card) {
        super(card);
    }

    @Override
    public DreamshaperShaman copy() {
        return new DreamshaperShaman(this);
    }
}
