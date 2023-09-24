

package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward
 */
public final class ShrineOfBurningRage extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("a red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ShrineOfBurningRage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        //At the beginning of your upkeep or whenever you cast a red spell, put a charge counter on Shrine of Burning Rage.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false),
                new SpellCastControllerTriggeredAbility(null, filter, false)));

        //{3}, {T}, Sacrifice Shrine of Burning Rage: It deals damage equal to the number of charge counters on it to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new CountersSourceCount(CounterType.CHARGE))
                .setText("it deals damage equal to the number of charge counters on it to any target"), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ShrineOfBurningRage(final ShrineOfBurningRage card) {
        super(card);
    }

    @Override
    public ShrineOfBurningRage copy() {
        return new ShrineOfBurningRage(this);
    }

}
