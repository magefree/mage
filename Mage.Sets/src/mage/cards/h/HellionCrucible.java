
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.HellionHasteToken;

/**
 *
 * @author jeffwadsworth
 */
public final class HellionCrucible extends CardImpl {

    public HellionCrucible(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}{R}, {tap}: Put a pressure counter on Hellion Crucible.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.PRESSURE.createInstance()), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {1}{R}, {tap}, Remove two pressure counters from Hellion Crucible and sacrifice it: Create a 4/4 red Hellion creature token with haste.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HellionHasteToken(), 1), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.PRESSURE.createInstance(2)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HellionCrucible(final HellionCrucible card) {
        super(card);
    }

    @Override
    public HellionCrucible copy() {
        return new HellionCrucible(this);
    }

}
