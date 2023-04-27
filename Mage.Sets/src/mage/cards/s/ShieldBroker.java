package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TargetHasCounterCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ShieldBroker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("noncommander creature you don't control");
    static {
        filter.add(Predicates.not(CommanderPredicate.instance));
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public ShieldBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.addSubType(SubType.CEPHALID, SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Shield Broker enters the battlefield, put a shield counter on target noncommander creature you don’t control.
        // You gain control of that creature for as long as it has a shield counter on it.
        // (If it would be dealt damage or destroyed, remove a shield counter from it instead.)
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.SHIELD.createInstance()));
        Effect gainControlEffect = new GainControlTargetEffect(Duration.Custom, false, null, new TargetHasCounterCondition(CounterType.SHIELD));
        gainControlEffect.setText("You gain control of that creature for as long as it has a shield counter on it. " +
                "<i>(If it would be dealt damage or destroyed, remove a shield counter from it instead.)</i>");
        etbAbility.addEffect(gainControlEffect);
        etbAbility.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(etbAbility);
    }

    private ShieldBroker(final ShieldBroker card) {
        super(card);
    }

    @Override
    public ShieldBroker copy() {
        return new ShieldBroker(this);
    }
}
