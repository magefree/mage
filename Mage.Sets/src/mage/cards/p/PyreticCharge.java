package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyreticCharge extends CardImpl {

    public PyreticCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Discard your hand, then draw four cards. For each card discarded this way, creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new PyreticChargeEffect());

        // Plot {3}{R}
        this.addAbility(new PlotAbility("{3}{R}"));
    }

    private PyreticCharge(final PyreticCharge card) {
        super(card);
    }

    @Override
    public PyreticCharge copy() {
        return new PyreticCharge(this);
    }
}

class PyreticChargeEffect extends OneShotEffect {

    PyreticChargeEffect() {
        super(Outcome.Benefit);
        staticText = "discard your hand, then draw four cards. For each card discarded this way, " +
                "creatures you control get +1/+0 until end of turn";
    }

    private PyreticChargeEffect(final PyreticChargeEffect effect) {
        super(effect);
    }

    @Override
    public PyreticChargeEffect copy() {
        return new PyreticChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = player.getHand().size();
        player.discard(count, false, false, source, game);
        player.drawCards(4, source, game);
        game.addEffect(new BoostControlledEffect(count, 0, Duration.EndOfTurn), source);
        return true;
    }
}
