package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SabertoothMauler extends CardImpl {

    public SabertoothMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if a creature died this turn, put a +1/+1 counter on Sabertooth Mauler and untap it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).withInterveningIf(MorbidCondition.instance).addHint(MorbidHint.instance);
        ability.addEffect(new UntapSourceEffect().setText("and untap it"));
        this.addAbility(ability);
    }

    private SabertoothMauler(final SabertoothMauler card) {
        super(card);
    }

    @Override
    public SabertoothMauler copy() {
        return new SabertoothMauler(this);
    }
}
