package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.watchers.common.RevoltWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MichelangeloGameMaster extends CardImpl {

    public MichelangeloGameMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your control this turn, put a +1/+1 counter on Michelangelo.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
            .withInterveningIf(RevoltCondition.instance);
        this.addAbility(ability.addHint(RevoltCondition.getHint()).setAbilityWord(AbilityWord.DISAPPEAR), new RevoltWatcher());

    }

    private MichelangeloGameMaster(final MichelangeloGameMaster card) {
        super(card);
    }

    @Override
    public MichelangeloGameMaster copy() {
        return new MichelangeloGameMaster(this);
    }
}
