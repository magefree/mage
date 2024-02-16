package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class WhirlwindDenial extends CardImpl {

    public WhirlwindDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // For each spell and ability your opponents control, counter it unless its controller pays {4}.
        this.getSpellAbility().addEffect(new WhirlwindDenialEffect());
    }

    private WhirlwindDenial(final WhirlwindDenial card) {
        super(card);
    }

    @Override
    public WhirlwindDenial copy() {
        return new WhirlwindDenial(this);
    }
}

class WhirlwindDenialEffect extends OneShotEffect {

    WhirlwindDenialEffect() {
        super(Outcome.Benefit);
        staticText = "For each spell and ability your opponents control, "
                + "counter it unless its controller pays {4}.";
    }

    private WhirlwindDenialEffect(final WhirlwindDenialEffect effect) {
        super(effect);
    }

    @Override
    public WhirlwindDenialEffect copy() {
        return new WhirlwindDenialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<StackObject> stackObjectsToCounter = new LinkedList<>();
        Cost cost = ManaUtil.createManaCost(4, false);
        // As Whirlwind Denial resolves, first the opponent whose turn it is
        // (or, if it's your turn, the next opponent in turn order) chooses which spells and/or abilities to pay for,
        // then pays that amount. Then each other opponent in turn order does the same.
        // Then all spells and abilities that weren't paid for are countered at the same time.
        // (2020-01-24)
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (playerId.equals(source.getControllerId())) {
                continue; // only opponents have to pay
            }
            Player player = game.getPlayer(playerId);
            for (StackObject stackObject : game.getStack()) {
                if (!playerId.equals(stackObject.getControllerId())) {
                    continue; // opponents only choose for their own spells/abilities
                }
                if (player == null) { // shouldn't be null, but if somehow so, they can't pay, so counter it
                    stackObjectsToCounter.add(stackObject);
                    continue;
                }
                if (cost.canPay(source, source, playerId, game)
                && player.chooseUse(outcome, "Pay {4} to prevent "
                        + stackObject.getIdName() + " from being countered?", source, game)
                        && cost.pay(source, game, source, stackObject.getControllerId(), false)) {
                    game.informPlayers(player.getLogName()
                            + " pays the cost to prevent "
                            + stackObject.getIdName()
                            + " from being countered.");
                } else {
                    game.informPlayers(stackObject.getIdName()
                            + " will be countered as "
                            + player.getLogName()
                            + " does not pay the cost.");
                    stackObjectsToCounter.add(stackObject); // will be countered all at the end
                }
            }
        }
        for (StackObject toCounter : stackObjectsToCounter) {
            game.getStack().counter(toCounter.getId(), source, game);
        }
        return true;
    }
}
