package mage.cards.i;

import mage.MageInt;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsatiableSkittermaw extends CardImpl {

    public InsatiableSkittermaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Void -- At the beginning of your end step, if a nonland permanent left the battlefield this turn or a spell was warped this turn, put a +1/+1 counter on this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).withInterveningIf(VoidCondition.instance).setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());
    }

    private InsatiableSkittermaw(final InsatiableSkittermaw card) {
        super(card);
    }

    @Override
    public InsatiableSkittermaw copy() {
        return new InsatiableSkittermaw(this);
    }
}
