package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
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
        game.getStack()
                .stream()
                .filter(Objects::nonNull)
                .forEachOrdered(stackObject -> {
                    if (!game.getOpponents(source.getControllerId()).contains(stackObject.getControllerId())) {
                        return;
                    }
                    Player player = game.getPlayer(stackObject.getControllerId());
                    if (player == null) {
                        return;
                    }
                    Cost cost = ManaUtil.createManaCost(4, false);
                    if (cost.canPay(source, source, stackObject.getControllerId(), game)
                            && player.chooseUse(outcome, "Pay {4} to prevent "
                            + stackObject.getIdName() + " from being countered?", source, game)
                            && cost.pay(source, game, source, stackObject.getControllerId(), false)) {
                        game.informPlayers("The cost was paid by "
                                + player.getLogName()
                                + " to prevent "
                                + stackObject.getIdName()
                                + " from being countered.");
                        return;
                    }
                    game.informPlayers("The cost was not paid by "
                            + player.getLogName()
                            + " to prevent "
                            + stackObject.getIdName()
                            + " from being countered.");
                    game.getStack().counter(stackObject.getId(), source, game);
                });
        return true;
    }
}
