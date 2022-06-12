
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LoneFox
 */
public final class ParallaxInhibitor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("permanent with fading you control");

    static {
        filter.add(new AbilityPredicate(FadingAbility.class));
    }

    public ParallaxInhibitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {tap}, Sacrifice Parallax Inhibitor: Put a fade counter on each permanent with fading you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new AddCountersAllEffect(CounterType.FADE.createInstance(), filter), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ParallaxInhibitor(final ParallaxInhibitor card) {
        super(card);
    }

    @Override
    public ParallaxInhibitor copy() {
        return new ParallaxInhibitor(this);
    }
}
