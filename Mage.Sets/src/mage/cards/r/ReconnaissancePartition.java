package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
 * @author muz
 */
public final class ReconnaissancePartition extends CardImpl {

    public ReconnaissancePartition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This creature enters with two charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2))));

        // {T}, Remove a counter from this creature: Choose one --
        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(1));

        // Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)));
        this.addAbility(ability);
    }

    private ReconnaissancePartition(final ReconnaissancePartition card) {
        super(card);
    }

    @Override
    public ReconnaissancePartition copy() {
        return new ReconnaissancePartition(this);
    }
}
