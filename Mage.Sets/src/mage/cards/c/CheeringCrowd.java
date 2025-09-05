package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class CheeringCrowd extends CardImpl {

    public CheeringCrowd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/G}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each player's first main phase, that player may put a +1/+1 counter on this creature. If they do, they add {C} for each counter on it.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(TargetController.EACH_PLAYER, new CheeringCrowdDoIfPaidEffect(), false));
    }

    private CheeringCrowd(final CheeringCrowd card) {
        super(card);
    }

    @Override
    public CheeringCrowd copy() {
        return new CheeringCrowd(this);
    }
}
class CheeringCrowdDoIfPaidEffect extends DoIfCostPaid {

    CheeringCrowdDoIfPaidEffect() {
        super(new CheeringCrowdEffect(), new PutCountersSourceCost(CounterType.P1P1.createInstance()),
                "Put a +1/+1 counter on Cheering Crowd and add {C} for each counter on it?", true);
        staticText = "that player may put a +1/+1 counter on this creature. If they do, they add {C} for each counter on it";
    }

    private CheeringCrowdDoIfPaidEffect(final CheeringCrowdDoIfPaidEffect effect) {
        super(effect);
    }

    @Override
    public CheeringCrowdDoIfPaidEffect copy() {
        return new CheeringCrowdDoIfPaidEffect(this);
    }

    @Override
    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(getTargetPointer().getFirst(game, source));
    }
}

class CheeringCrowdEffect extends OneShotEffect {

    public CheeringCrowdEffect() {
        super(Outcome.PutManaInPool);
        staticText = "they add {C} for each counter on it";
    }

    protected CheeringCrowdEffect(final CheeringCrowdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        int counterCounter = permanent.getCounters(game).getCount(CounterType.P1P1);
        player.getManaPool().addMana(Mana.ColorlessMana(counterCounter), game, source);
        return true;
    }

    @Override
    public CheeringCrowdEffect copy() {
        return new CheeringCrowdEffect(this);
    }
}
