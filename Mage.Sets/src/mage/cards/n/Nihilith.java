
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class Nihilith extends CardImpl {

    public Nihilith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Fear
        this.addAbility(FearAbility.getInstance());
        
        // Suspend 7-{1}{B}
        this.addAbility(new SuspendAbility(7, new ManaCostsImpl<>("{1}{B}"), this, false));
        
        // Whenever a card is put into an opponent's graveyard from anywhere, if Nihilith is suspended, you may remove a time counter from Nihilith.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new PutCardIntoGraveFromAnywhereAllTriggeredAbility(Zone.EXILED, new RemoveCounterSourceEffect(CounterType.TIME.createInstance()), true, 
                        new FilterCard(), TargetController.OPPONENT, SetTargetPointer.NONE),
                SuspendedCondition.instance,
                "Whenever a card is put into an opponent's graveyard from anywhere, if {this} is suspended, you may remove a time counter from {this}."
                ));        
        
    }

    private Nihilith(final Nihilith card) {
        super(card);
    }

    @Override
    public Nihilith copy() {
        return new Nihilith(this);
    }
}
