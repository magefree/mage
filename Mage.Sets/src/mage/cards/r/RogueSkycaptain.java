package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
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
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.util.ManaUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Ketsuban
 */
public class RogueSkycaptain extends CardImpl {

    public RogueSkycaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put a wage counter on Rogue Skycaptain. You
        // may pay 2 for each wage counter on it. If you don't, remove all wage counters
        // from Rogue Skycaptain and an opponent gains control of it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RogueSkycaptainEffect(), TargetController.YOU, false));
    }

    private RogueSkycaptain(final RogueSkycaptain card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new RogueSkycaptain(this);
    }

}

class RogueSkycaptainEffect extends OneShotEffect {

    public RogueSkycaptainEffect() {
        super(Outcome.GainControl);
        staticText = "put a wage counter on {this}. You may pay {2} for each wage counter on it. If you don't, remove all wage counters from {this} and an opponent gains control of it";
    }

    public RogueSkycaptainEffect(final RogueSkycaptainEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new RogueSkycaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && permanent != null) {
            new AddCountersSourceEffect(CounterType.WAGE.createInstance(), true).apply(game, source);
            Cost cost = ManaUtil.createManaCost(2 * permanent.getCounters(game).getCount(CounterType.WAGE), false);
            if (!cost.pay(source, game, source, controller.getId(), false)) {
                new RemoveAllCountersSourceEffect(CounterType.WAGE).apply(game, source);
                Player opponent;
                Set<UUID> opponents = game.getOpponents(controller.getId());
                if (opponents.size() == 1) {
                    opponent = game.getPlayer(opponents.iterator().next());
                } else {
                    Target target = new TargetOpponent(true);
                    target.setNotTarget(true);
                    target.choose(Outcome.GainControl, source.getControllerId(), source.getSourceId(), source, game);
                    opponent = game.getPlayer(target.getFirstTarget());
                }
                if (opponent != null) {
                    permanent.changeControllerId(opponent.getId(), game, source);
                }
            }
            return true;
        }
        return false;
    }
}