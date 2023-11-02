
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.DragonToken2;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class DeathByDragons extends CardImpl {

    public DeathByDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");


        // Each player other than target player creates a 5/5 red Dragon creature token with flying.
        this.getSpellAbility().addEffect(new DeathByDragonsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DeathByDragons(final DeathByDragons card) {
        super(card);
    }

    @Override
    public DeathByDragons copy() {
        return new DeathByDragons(this);
    }
}

class DeathByDragonsEffect extends OneShotEffect {

    public DeathByDragonsEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player other than target player creates a 5/5 red Dragon creature token with flying";
    }

    private DeathByDragonsEffect(final DeathByDragonsEffect effect) {
        super(effect);
    }

    @Override
    public DeathByDragonsEffect copy() {
        return new DeathByDragonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(this.getTargetPointer().getFirst(game, source))) {
                    Token token = new DragonToken2();
                    token.putOntoBattlefield(1, game, source, playerId);
                }
            }
            return true;
        }
        return false;
    }
}
