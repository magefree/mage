
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author ilcartographer
 */
public final class Fertilid extends CardImpl {

    public Fertilid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Fertilid enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), "with two +1/+1 counters on it"));
        
        // {1}{G}, Remove a +1/+1 counter from Fertilid: Target player searches their library for a basic land card and puts it onto the battlefield tapped. Then that player shuffles their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new SearchLibraryPutInPlayTargetPlayerEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Fertilid(final Fertilid card) {
        super(card);
    }

    @Override
    public Fertilid copy() {
        return new Fertilid(this);
    }
}
