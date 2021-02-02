package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
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
        this.addAbility(new AsEntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // Pay {1} for each +1/+1 counter on Skeleton Scavengers: Regenerate Skeleton Scavengers. When it regenerates this way, put a +1/+1 counter on it.
        this.addAbility(new SimpleActivatedAbility(new SkeletonScavengersEffect(), new DynamicValueGenericManaCost(new CountersSourceCount(CounterType.P1P1))));

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

    public DynamicValueGenericManaCost(DynamicValueGenericManaCost cost) {
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

class SkeletonScavengersEffect extends OneShotEffect {

    SkeletonScavengersEffect() {
        super(Outcome.Benefit);
        this.staticText = "Regenerate {this}. When it regenerates this way, put a +1/+1 counter on it";
    }

    SkeletonScavengersEffect(final SkeletonScavengersEffect effect) {
        super(effect);
    }

    @Override
    public SkeletonScavengersEffect copy() {
        return new SkeletonScavengersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent skeletonScavengers = game.getPermanent(source.getSourceId());
        if (skeletonScavengers != null) {
            if (new RegenerateSourceEffect().apply(game, source)) {
                return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
            }
        }
        return false;
    }
}
