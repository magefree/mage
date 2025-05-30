package mage.cards.c;

import mage.MageInt;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CacklingProwler extends CardImpl {

    public CacklingProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HYENA);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Morbid -- At the beginning of your end step, if a creature died this turn put a +1/+1 counter on this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).withInterveningIf(MorbidCondition.instance).setAbilityWord(AbilityWord.MORBID).addHint(MorbidHint.instance));
    }

    private CacklingProwler(final CacklingProwler card) {
        super(card);
    }

    @Override
    public CacklingProwler copy() {
        return new CacklingProwler(this);
    }
}
