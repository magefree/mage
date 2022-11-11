package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPermanentAmount;

/**
 *
 * @author weirddan455
 */
public final class QuirionBeastcaller extends CardImpl {

    public QuirionBeastcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell, put a +1/+1 counter on Quirion Beastcaller.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_CREATURE,
                false
        ));

        // When Quirion Beastcaller dies, distribute X +1/+1 counters among any number of target creatures you control, where X is the number of +1/+1 counters on Quirion Beastcaller.
        Ability ability = new DiesSourceTriggeredAbility(new DistributeCountersEffect(
                // Amount here is only used for text generation.  Real amount is set in target.
                CounterType.P1P1, 1, false, "any number of target creatures you control"
        ).setText("distribute X +1/+1 counters among any number of target creatures you control, where X is the number of +1/+1 counters on {this}"));
        ability.addTarget(new TargetPermanentAmount(new CountersSourceCount(CounterType.P1P1), StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.addAbility(ability);
    }

    private QuirionBeastcaller(final QuirionBeastcaller card) {
        super(card);
    }

    @Override
    public QuirionBeastcaller copy() {
        return new QuirionBeastcaller(this);
    }
}
