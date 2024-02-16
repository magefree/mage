
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class RingOfThreeWishes extends CardImpl {

    public RingOfThreeWishes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Ring of Three Wishes enters the battlefield with three wish counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.WISH.createInstance(3)), "with three wish counters on it"));
        // {5}, {T}, Remove a wish counter from Ring of Three Wishes: Search your library for a card and put that card into your hand. Then shuffle your library. 
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.WISH.createInstance()));
        this.addAbility(ability);
    }

    private RingOfThreeWishes(final RingOfThreeWishes card) {
        super(card);
    }

    @Override
    public RingOfThreeWishes copy() {
        return new RingOfThreeWishes(this);
    }
}
