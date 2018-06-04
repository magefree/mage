
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class RepayInKind extends CardImpl {

    public RepayInKind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}{B}");


        // Each player's life total becomes the lowest life total among all players.
        this.getSpellAbility().addEffect(new RepayInKindEffect());
    }

    public RepayInKind(final RepayInKind card) {
        super(card);
    }

    @Override
    public RepayInKind copy() {
        return new RepayInKind(this);
    }
}

class RepayInKindEffect extends OneShotEffect {

    public RepayInKindEffect() {
        super(Outcome.Tap);
        staticText = "Each player's life total becomes the lowest life total among all players";
    }

    public RepayInKindEffect(final RepayInKindEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lowestLife = game.getPlayer(source.getControllerId()).getLife();
        for (Player playerid : game.getPlayers().values()) {
            if (playerid != null) {
                if (lowestLife > playerid.getLife()) {
                    lowestLife = playerid.getLife();
                }
            }  
        }
        for (Player playerId : game.getPlayers().values()) {
            if (playerId != null) {
                playerId.setLife(lowestLife, game, source);
            }
        }
        return true;
    }

    @Override
    public RepayInKindEffect copy() {
        return new RepayInKindEffect(this);
    }

}
