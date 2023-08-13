package mage.cards.k;

import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Viserion
 */
public final class KhalniHeartExpedition extends CardImpl {

    public KhalniHeartExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));

        this.addAbility(new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                        0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS
                ), true),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)),
                        new SacrificeSourceCost(),
                        "Remove three quest counters from {this} and sacrifice it"
                )
        ));
    }

    private KhalniHeartExpedition(final KhalniHeartExpedition card) {
        super(card);
    }

    @Override
    public KhalniHeartExpedition copy() {
        return new KhalniHeartExpedition(this);
    }
}
