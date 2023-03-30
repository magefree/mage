package mage.cards.s;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class SithWayfinder extends CardImpl {
    public SithWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        //{2}, {T}, Sacrifice Sith Wayfinder: Search your library for a land card, reveal it, and put it into your hand.
        //Then shuffle your library.
        SimpleActivatedAbility simpleActivatedAbility = new SimpleActivatedAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND), true, true),
                new ManaCostsImpl<>("{2}"));
        simpleActivatedAbility.addCost(new TapSourceCost());
        simpleActivatedAbility.addCost(new SacrificeSourceCost());
        this.addAbility(simpleActivatedAbility);
    }

    public SithWayfinder(final SithWayfinder card) {
        super(card);
    }

    @Override
    public SithWayfinder copy() {
        return new SithWayfinder(this);
    }
}
