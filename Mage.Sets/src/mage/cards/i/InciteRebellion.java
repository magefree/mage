package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InciteRebellion extends CardImpl {

    public InciteRebellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");


        // For each player, Incite Rebellion deals damage to that player and each creature that player controls equal to the number of creatures they control.
        this.getSpellAbility().addEffect(new InciteRebellionEffect());
    }

    private InciteRebellion(final InciteRebellion card) {
        super(card);
    }

    @Override
    public InciteRebellion copy() {
        return new InciteRebellion(this);
    }
}

class InciteRebellionEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public InciteRebellionEffect() {
        super(Outcome.Detriment);
        this.staticText = "For each player, {this} deals damage to that player and each creature that player controls equal to the number of creatures they control";
    }

    private InciteRebellionEffect(final InciteRebellionEffect effect) {
        super(effect);
    }

    @Override
    public InciteRebellionEffect copy() {
        return new InciteRebellionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = game.getBattlefield().countAll(filter, playerId, game);
                    if (count > 0) {
                        player.damage(count, source.getSourceId(), source, game);
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                            permanent.damage(count, source.getSourceId(), source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
