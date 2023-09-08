package mage.cards.h;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.TapUntappedPermanentTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Elemental44WUToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HyldaOfTheIcyCrown extends CardImpl {

    public HyldaOfTheIcyCrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you tap an untapped creature an opponent controls, you may pay {1}. When you do, choose one --
        // * Create a 4/4 white and blue Elemental creature token.
        ReflexiveTriggeredAbility delayed = new ReflexiveTriggeredAbility(
                new CreateTokenEffect(new Elemental44WUToken()), false
        );
        // * Put a +1/+1 counter on each creature you control.
        delayed.addMode(new Mode(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));
        
        // * Scry 2, then draw a card.
        Mode mode = new Mode(new ScryEffect(2, false));
        mode.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        delayed.addMode(mode);

        this.addAbility(new TapUntappedPermanentTriggeredAbility(
                new DoWhenCostPaid(delayed, new GenericManaCost(1), "Pay {1}?"),
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ));
    }

    private HyldaOfTheIcyCrown(final HyldaOfTheIcyCrown card) {
        super(card);
    }

    @Override
    public HyldaOfTheIcyCrown copy() {
        return new HyldaOfTheIcyCrown(this);
    }
}
