package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.common.MillTriggeredAbility;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class InfestingRadroach extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard();

    public InfestingRadroach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Infesting Radroach can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever Infesting Radroach deals combat damage to a player, they get that many rad counters.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.RAD.createInstance(), SavedDamageValue.MANY).setText("they get that many rad counters"),
                false, true
        ));

        // Whenever an opponent mills a nonland card, if Infesting Radroach is in your graveyard, you may return it to your hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new MillTriggeredAbility(Zone.GRAVEYARD, new ReturnToHandSourceEffect(), TargetController.OPPONENT, filter, true),
                SourceInGraveyardCondition.instance, "Whenever an opponent mills a nonland card, "
                + "if {this} is in your graveyard, you may return it to your hand"
        ));
    }

    private InfestingRadroach(final InfestingRadroach card) {
        super(card);
    }

    @Override
    public InfestingRadroach copy() {
        return new InfestingRadroach(this);
    }
}
