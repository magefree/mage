package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.RegenerateSourceWithReflexiveEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SkeletonScavengers extends CardImpl {

    public SkeletonScavengers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Skeleton Scavengers enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "with a +1/+1 counter on it"));

        // Pay {1} for each +1/+1 counter on Skeleton Scavengers: Regenerate Skeleton Scavengers. When it regenerates this way, put a +1/+1 counter on it.
        this.addAbility(new SimpleActivatedAbility(
                new RegenerateSourceWithReflexiveEffect(new ReflexiveTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        false
                ), false),
                new DynamicValueGenericManaCost(new CountersSourceCount(CounterType.P1P1))
        ));

    }

    private SkeletonScavengers(final SkeletonScavengers card) {
        super(card);
    }

    @Override
    public SkeletonScavengers copy() {
        return new SkeletonScavengers(this);
    }
}

class DynamicValueGenericManaCost extends CostImpl {

    DynamicValue amount;

    public DynamicValueGenericManaCost(DynamicValue amount) {
        this.amount = amount;
        setText();
    }

    private DynamicValueGenericManaCost(final DynamicValueGenericManaCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        Cost cost = ManaUtil.createManaCost(amount, game, ability, null);
        return cost.canPay(ability, source, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }

        Cost cost = ManaUtil.createManaCost(amount, game, ability, null);
        paid = cost.pay(ability, game, source, controllerId, noMana);

        return paid;
    }

    @Override
    public DynamicValueGenericManaCost copy() {
        return new DynamicValueGenericManaCost(this);
    }

    private void setText() {
        text = ("Pay {1} for each +1/+1 counter on {this}");
    }
}