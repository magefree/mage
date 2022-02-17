
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ScaleBlessing extends CardImpl {

    public ScaleBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Bolster 1,
        this.getSpellAbility().addEffect(new BolsterEffect(1).setText("Bolster 1, "));

        // then put a +1/+1 counter on each creature you control with a +1/+1 counter on it. <i.(To bolster 1, choose a creature with the least toughness among creatures you control and put +1/+1 counter on it.)</i>
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1
        ).setText("then put a +1/+1 counter on each creature you control with a +1/+1 counter on it. " +
                "<i>(To bolster 1, choose a creature with the least toughness among creatures you control " +
                "and put a +1/+1 counter on it.)</i>"));
    }

    private ScaleBlessing(final ScaleBlessing card) {
        super(card);
    }

    @Override
    public ScaleBlessing copy() {
        return new ScaleBlessing(this);
    }
}
