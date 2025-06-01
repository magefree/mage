package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Jmlundeen
 */
public final class RampagingAetherhood extends CardImpl {

    private static final String energyText = "you get an amount of {E} equal to this creature's power.";

    public RampagingAetherhood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // At the beginning of your upkeep, you get an amount of {E} equal to this creature's power. Then you may pay one or more {E}. If you do, put that many +1/+1 counters on this creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RampagingAetherhoodEffect()));
    }

    private RampagingAetherhood(final RampagingAetherhood card) {
        super(card);
    }

    @Override
    public RampagingAetherhood copy() {
        return new RampagingAetherhood(this);
    }
}

class RampagingAetherhoodEffect extends OneShotEffect {

    public RampagingAetherhoodEffect() {
        super(Outcome.Benefit);
        this.staticText = "you get an amount of {E} equal to this creature's power. " +
                "Then you may pay one or more {E}. If you do, put that many +1/+1 counters on this creature.";
    }

    public RampagingAetherhoodEffect(final RampagingAetherhoodEffect effect) {
        super(effect);
    }

    @Override
    public RampagingAetherhoodEffect copy() {
        return new RampagingAetherhoodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null) {
            return false;
        }
        int amount = permanent.getPower().getValue();
        controller.addCounters(CounterType.ENERGY.createInstance(amount), source.getControllerId(), source, game);
        int totalEnergy = controller.getCountersCount(CounterType.ENERGY);
        if (totalEnergy > 0) {
            if (controller.chooseUse(Outcome.Benefit, "Pay one or more {E}? Put that many +1/+1 counters on this creature", source, game)) {
                int energyToPay = controller.getAmount(1, totalEnergy, "Pay one or more {E}", source, game);
                Cost cost = new PayEnergyCost(energyToPay);
                if (cost.pay(source, game, source, controller.getId(), true)) {
                    new AddCountersSourceEffect(CounterType.P1P1.createInstance(energyToPay), true).apply(game, source);
                    return true;
                }
            }
        }
        return false;
    }
}