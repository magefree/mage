
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PriceOfProgress extends CardImpl {

    public PriceOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Price of Progress deals damage to each player equal to twice the number of nonbasic lands that player controls.
        this.getSpellAbility().addEffect(new PriceOfProgressEffect());
    }

    public PriceOfProgress(final PriceOfProgress card) {
        super(card);
    }

    @Override
    public PriceOfProgress copy() {
        return new PriceOfProgress(this);
    }
}

class PriceOfProgressEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();

    public PriceOfProgressEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage to each player equal to twice the number of nonbasic lands that player controls";
    }

    public PriceOfProgressEffect(final PriceOfProgressEffect effect) {
        super(effect);
    }

    @Override
    public PriceOfProgressEffect copy() {
        return new PriceOfProgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                int amount = game.getBattlefield().countAll(filter , playerId, game);
                if (amount > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(amount * 2, source.getSourceId(), game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
