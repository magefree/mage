

package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class ShrineOfBoundlessGrowth extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a green spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public ShrineOfBoundlessGrowth (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // At the beginning of your upkeep or whenever you cast a green spell, put a charge counter on Shrine of Boundless Growth.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false),
                new SpellCastControllerTriggeredAbility(null, filter, false)));

        // {T}, Sacrifice Shrine of Boundless Growth: Add {C} for each charge counter on Shrine of Boundless Growth.
        Ability ability = new DynamicManaAbility(Mana.ColorlessMana(1), new CountersSourceCount(CounterType.CHARGE), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private ShrineOfBoundlessGrowth(final ShrineOfBoundlessGrowth card) {
        super(card);
    }

    @Override
    public ShrineOfBoundlessGrowth copy() {
        return new ShrineOfBoundlessGrowth(this);
    }

}
