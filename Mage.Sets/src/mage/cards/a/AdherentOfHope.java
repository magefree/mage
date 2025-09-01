package mage.cards.a;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class AdherentOfHope extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPlaneswalkerPermanent(SubType.BASRI, "you control a Basri planeswalker")
    );
    private static final Hint hint = new ConditionHint(condition);

    public AdherentOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control a Basri planeswalker, put a +1/+1 counter on Adherent of Hope.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())).withInterveningIf(condition).addHint(hint));
    }

    private AdherentOfHope(final AdherentOfHope card) {
        super(card);
    }

    @Override
    public AdherentOfHope copy() {
        return new AdherentOfHope(this);
    }
}
