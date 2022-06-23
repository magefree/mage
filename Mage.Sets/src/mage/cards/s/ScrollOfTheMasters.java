
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ScrollOfTheMasters extends CardImpl {

    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ScrollOfTheMasters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever you cast a noncreature spell, put a lore counter on Scroll of the Masters.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.LORE.createInstance()), filterNonCreature, false));

        // {3}, {T}: Target creature you control gets +1/+1 until end of turn for each lore counter on Scroll of the Masters.
        DynamicValue xValue = new CountersSourceCount(CounterType.LORE);
        Effect effect = new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +1/+1 until end of turn for each lore counter on {this}");
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ScrollOfTheMasters(final ScrollOfTheMasters card) {
        super(card);
    }

    @Override
    public ScrollOfTheMasters copy() {
        return new ScrollOfTheMasters(this);
    }
}
