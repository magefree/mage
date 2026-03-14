package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CreakwoodSafewright extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            "there is an Elf card in your graveyard and {this} has a -1/-1 counter on it",
            new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.ELF)),
            new SourceHasCounterCondition(CounterType.M1M1)
    );
    private static final Hint hint = new ConditionHint(
            new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.ELF)),
            "There is an Elf card in your graveyard"
    );

    public CreakwoodSafewright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This creature enters with three -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(3)));

        // At the beginning of your end step, if there is an Elf card in your graveyard and this creature has a -1/-1 counter on it, remove a -1/-1 counter from this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.M1M1.createInstance())
        ).withInterveningIf(condition).addHint(hint));
    }

    private CreakwoodSafewright(final CreakwoodSafewright card) {
        super(card);
    }

    @Override
    public CreakwoodSafewright copy() {
        return new CreakwoodSafewright(this);
    }
}
