package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeologyEnthusiast extends CardImpl {

    public GeologyEnthusiast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, create a tapped Powerstone token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(
                        new PowerstoneToken(), 1, true
                ), TargetController.YOU, false
        ));

        // {6}: Draw a card and put a +1/+1 counter on Geology Enthusiast.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(6));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        this.addAbility(ability);
    }

    private GeologyEnthusiast(final GeologyEnthusiast card) {
        super(card);
    }

    @Override
    public GeologyEnthusiast copy() {
        return new GeologyEnthusiast(this);
    }
}
