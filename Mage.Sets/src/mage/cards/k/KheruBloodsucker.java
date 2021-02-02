
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class KheruBloodsucker extends CardImpl {

    private static final FilterCreaturePermanent toughnessFilter = new FilterCreaturePermanent("a creature you control with toughness 4 or greater");

    static {
        toughnessFilter.add(TargetController.YOU.getControllerPredicate());
        toughnessFilter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }

    public KheruBloodsucker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control with toughness 4 or greater dies, each opponent loses 2 life and you gain 2 life.
        Ability ability = new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(2), false, toughnessFilter);
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {2}{B}, Sacrifice another creature: Put a +1/+1 counter on Kheru Bloodsucker.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);
    }

    private KheruBloodsucker(final KheruBloodsucker card) {
        super(card);
    }

    @Override
    public KheruBloodsucker copy() {
        return new KheruBloodsucker(this);
    }
}
