package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class AdherentOfHope extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.BASRI.getPredicate());
    }

    public AdherentOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control a Basri planeswalker, put a +1/+1 counter on Adherent of Hope.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "At the beginning of combat on your turn, if you control a Basri planeswalker, put a +1/+1 counter on {this}."));
    }

    private AdherentOfHope(final AdherentOfHope card) {
        super(card);
    }

    @Override
    public AdherentOfHope copy() {
        return new AdherentOfHope(this);
    }
}
