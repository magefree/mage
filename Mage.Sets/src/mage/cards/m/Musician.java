package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Musician extends CardImpl {

    public Musician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));

        // {tap}: Put a music counter on target creature. If it doesn't have "At the beginning of your upkeep, destroy this creature unless you pay {1} for each music counter on it," it gains that ability.
        Effect effect = new DoUnlessControllerPaysEffect(
                new DestroySourceEffect(),
                new DynamicValueGenericManaCost(
                        new CountersSourceCount(
                                CounterType.MUSIC)));
        effect.setText("destroy this creature unless you pay {1} for each music counter on it");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                effect,
                TargetController.YOU,
                false,
                false,
                "At the beginning of your upkeep, ");
        Effect effect2 = new AddCountersTargetEffect(CounterType.MUSIC.createInstance());
        effect2.setText("Put a music counter on target creature");
        Effect effect3 = new GainAbilityTargetEffect(
                ability,
                Duration.WhileOnBattlefield);
        effect3.setText("If it doesn't have \"At the beginning of your upkeep, destroy this creature unless you pay {1} for each music counter on it,\" it gains that ability");
        Ability ability2 = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                effect2,
                new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addEffect(effect3);
        this.addAbility(ability2);

    }

    private Musician(final Musician card) {
        super(card);
    }

    @Override
    public Musician copy() {
        return new Musician(this);
    }
}

class DynamicValueGenericManaCost extends CostImpl {

    DynamicValue amount;

    public DynamicValueGenericManaCost(DynamicValue amount) {
        this.amount = amount;
        setText();
    }

    public DynamicValueGenericManaCost(DynamicValueGenericManaCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        int convertedCost = amount.calculate(game, ability, null);
        Cost cost = new GenericManaCost(convertedCost);
        return cost.canPay(ability, sourceId, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        int convertedCost = amount.calculate(game, ability, null);
        Cost cost = new GenericManaCost(convertedCost);
        if (controller != null) {
            paid = cost.pay(ability, game, sourceId, controllerId, noMana);
        }
        return paid;
    }

    @Override
    public DynamicValueGenericManaCost copy() {
        return new DynamicValueGenericManaCost(this);
    }

    private void setText() {
        text = ("{1} for each music counter on {this}");
    }
}
