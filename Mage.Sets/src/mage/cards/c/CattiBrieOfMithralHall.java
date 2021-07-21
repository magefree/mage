package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
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
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), amount, false)));

        // {1}, Remove all +1/+1 counters from Catti-brie: It deals X damage to target
        // attacking or blocking creature an opponent controls, where X is the number of
        // counters removed this way.
        Ability damageAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CattiBrieOfMithralHallEffect(),
                new ManaCostsImpl("{1}"));
        damageAbility.addTarget(new TargetCreaturePermanent(filter));

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

class CattiBrieOfMithralHallEffect extends OneShotEffect {

    CattiBrieOfMithralHallEffect() {
        super(Outcome.Damage);
        staticText = "Remove all +1/+1 counters from {this}, deal X damage to target attacking or blocking creature an opponent controls, where X is the number of counters removed this way.";
    }

    CattiBrieOfMithralHallEffect(CattiBrieOfMithralHallEffect effect) {
        super(effect);
    }

    @Override
    public CattiBrieOfMithralHallEffect copy() {
        return new CattiBrieOfMithralHallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (permanent != null && target != null) { // Can't activate this without a valid target
            Integer numberOfCounters = permanent.getCounters(game).getCount(CounterType.P1P1);
            if (numberOfCounters == null || numberOfCounters < 1) {
                return false;
            }

            // Remove all +1/+1 counters
            permanent.removeCounters(permanent.getCounters(game).get(CounterType.P1P1.getName()), source, game);
            // Deal X damage
            target.damage(numberOfCounters, source.getSourceId(), source, game, false, true);
            return true;
        }
        return false;
    }

}