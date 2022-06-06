package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author zeffirojoe
 */
public final class CattiBrieOfMithralHall extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature(
            "attacking or blocking creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public CattiBrieOfMithralHall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Catti-brie of Mithral Hall attacks, put a +1/+1 counter on it for
        // each Equipment attached to it.
        EquipmentAttachedCount amount = new EquipmentAttachedCount();
        this.addAbility(new AttacksTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), amount, false).setText("put a +1/+1 counter on it for each Equipment attached to it")));

        // {1}, Remove all +1/+1 counters from Catti-brie: It deals X damage to target
        // attacking or blocking creature an opponent controls, where X is the number of
        // counters removed this way.
        Ability damageAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(CattiBrieRemovedCounterValue.instance).setText("it deals X damage to target attacking or blocking creature an opponent controls, where X is the number of counters removed this way"), new ManaCostsImpl<>("{1}"));
        damageAbility.addTarget(new TargetCreaturePermanent(filter));
        damageAbility.addCost(new RemoveAllCountersSourceCost(CounterType.P1P1));

        this.addAbility(damageAbility);
    }

    private CattiBrieOfMithralHall(final CattiBrieOfMithralHall card) {
        super(card);
    }

    @Override
    public CattiBrieOfMithralHall copy() {
        return new CattiBrieOfMithralHall(this);
    }
}

enum CattiBrieRemovedCounterValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int countersRemoved = 0;
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        return countersRemoved;
    }

    @Override
    public CattiBrieRemovedCounterValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}