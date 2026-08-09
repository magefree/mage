package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NastyLittleRabbit extends CardImpl {

    public NastyLittleRabbit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.RABBIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ferocious -- At the beginning of combat on your turn, if you control a creature with power 4 or greater, put a +1/+1 counter on this creature.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
            .withInterveningIf(FerociousCondition.instance)
            .setAbilityWord(AbilityWord.FEROCIOUS)
            .addHint(FerociousHint.instance)
        );
    }

    private NastyLittleRabbit(final NastyLittleRabbit card) {
        super(card);
    }

    @Override
    public NastyLittleRabbit copy() {
        return new NastyLittleRabbit(this);
    }
}
