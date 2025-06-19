package mage.cards.a;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AjanisComrade extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPlaneswalkerPermanent(SubType.AJANI, "you control an Ajani planeswalker")
    );

    public AjanisComrade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, if you control an Ajani planeswalker, put a +1/+1 counter on Ajani's Comrade.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())).withInterveningIf(condition));
    }

    private AjanisComrade(final AjanisComrade card) {
        super(card);
    }

    @Override
    public AjanisComrade copy() {
        return new AjanisComrade(this);
    }
}
