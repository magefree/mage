

package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class ArcaneSpyglass extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public ArcaneSpyglass (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {T} , Sacrifice a land: Draw a card and put a charge counter on Arcane Spyglass.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // Remove three charge counters from Arcane Spyglass: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(3))));
    }

    private ArcaneSpyglass(final ArcaneSpyglass card) {
        super(card);
    }

    @Override
    public ArcaneSpyglass copy() {
        return new ArcaneSpyglass(this);
    }

}
