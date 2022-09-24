package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PurestrainGenestealer extends CardImpl {

    public PurestrainGenestealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Purestrain Genestealer enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(2)
        ), "with two +1/+1 counters on it"));

        // Vanguard Species -- Whenever Purestrain Genestealer attacks, you may remove a +1/+1 counter from it. If you do, search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
                ), new RemoveCountersSourceCost(CounterType.P1P1.createInstance())
                        .setText("remove a +1/+1 counter from it"))
        ).withFlavorWord("Vanguard Species"));
    }

    private PurestrainGenestealer(final PurestrainGenestealer card) {
        super(card);
    }

    @Override
    public PurestrainGenestealer copy() {
        return new PurestrainGenestealer(this);
    }
}
