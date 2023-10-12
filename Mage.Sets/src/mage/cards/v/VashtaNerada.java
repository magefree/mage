package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VashtaNerada extends CardImpl {

    public VashtaNerada(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // Morbid — At the beginning of each end step, if a creature died this turn, put a +1/+1 counter on Vashta Nerada.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.ANY, MorbidCondition.instance, false
        ).addHint(MorbidHint.instance).setAbilityWord(AbilityWord.MORBID));
    }

    private VashtaNerada(final VashtaNerada card) {
        super(card);
    }

    @Override
    public VashtaNerada copy() {
        return new VashtaNerada(this);
    }
}
