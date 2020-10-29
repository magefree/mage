package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.PhyrexianRebirthHorrorToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfaneTransfusion extends CardImpl {

    public ProfaneTransfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}{B}{B}");

        // Two target players exchange life totals. You create an X/X colorless Horror artifact creature token, where X is the difference between those players' life totals.
        this.getSpellAbility().addEffect(new ProfaneTransfusionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(2));
    }

    private ProfaneTransfusion(final ProfaneTransfusion card) {
        super(card);
    }

    @Override
    public ProfaneTransfusion copy() {
        return new ProfaneTransfusion(this);
    }
}

class ProfaneTransfusionEffect extends OneShotEffect {

    ProfaneTransfusionEffect() {
        super(Outcome.Benefit);
        staticText = "two target players exchange life totals. " +
                "You create an X/X colorless Horror artifact creature token, " +
                "where X is the difference between those players' life totals";
    }

    private ProfaneTransfusionEffect(final ProfaneTransfusionEffect effect) {
        super(effect);
    }

    @Override
    public ProfaneTransfusionEffect copy() {
        return new ProfaneTransfusionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().get(0).getTargets().size() < 2) {
            return false;
        }
        Player player1 = game.getPlayer(source.getTargets().get(0).getTargets().get(0));
        Player player2 = game.getPlayer(source.getTargets().get(0).getTargets().get(1));
        if (player1 == null || player2 == null) {
            return false;
        }
        int lifePlayer1 = player1.getLife();
        int lifePlayer2 = player2.getLife();
        int lifeDifference = Math.abs(lifePlayer1 - lifePlayer2);

        Token token = new PhyrexianRebirthHorrorToken();
        token.setPower(lifeDifference);
        token.setToughness(lifeDifference);

        if (lifeDifference == 0
                || !player1.isLifeTotalCanChange()
                || !player2.isLifeTotalCanChange()) {
            return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        }

        if (lifePlayer1 < lifePlayer2 && (!player1.isCanGainLife() || !player2.isCanLoseLife())) {
            return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        }

        if (lifePlayer1 > lifePlayer2 && (!player1.isCanLoseLife() || !player2.isCanGainLife())) {
            return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        }

        player1.setLife(lifePlayer2, game, source);
        player2.setLife(lifePlayer1, game, source);
        return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
    }
}
