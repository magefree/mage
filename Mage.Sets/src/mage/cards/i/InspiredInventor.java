package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.ServoToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiredInventor extends CardImpl {

    public InspiredInventor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Inspired Inventor enters the battlefield, choose one --
        // * You get {E}{E}{E}.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3));

        // * Put a +1/+1 counter on target creature.
        ability.addMode(new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()))
                .addTarget(new TargetCreaturePermanent()));

        // * Create a 1/1 colorless Servo artifact creature token.
        ability.addMode(new Mode(new CreateTokenEffect(new ServoToken())));
        this.addAbility(ability);
    }

    private InspiredInventor(final InspiredInventor card) {
        super(card);
    }

    @Override
    public InspiredInventor copy() {
        return new InspiredInventor(this);
    }
}
